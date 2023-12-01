package com.knits.enterprise.service.asset;

import com.knits.enterprise.dto.data.asset.AssetModelDto;
import com.knits.enterprise.exceptions.UserException;
import com.knits.enterprise.mapper.asset.AssetModelMapper;
import com.knits.enterprise.mapper.asset.AssetTechSpecMapper;
import com.knits.enterprise.model.asset.AssetModel;
import com.knits.enterprise.model.asset.AssetTechSpec;
import com.knits.enterprise.model.asset.Category;
import com.knits.enterprise.repository.asset.AssetModelRepository;
import com.knits.enterprise.repository.asset.AssetTechSpecRepository;
import com.knits.enterprise.repository.asset.CategoryRepository;
import com.knits.enterprise.service.common.GenericService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class AssetModelService extends GenericService {

    private final AssetModelMapper assetModelMapper;
    private final CategoryRepository categoryRepository;
    private final AssetModelRepository assetModelRepository;
    private final AssetTechSpecMapper assetTechSpecMapper;
    private final AssetTechSpecRepository assetTechSpecRepository;


    public AssetModelDto create(AssetModelDto assetModelDto) {
        String operationLog ="Request to create assetModel : %s".formatted(assetModelDto.toString());
        logCurrentUser(operationLog);

        AssetModel assetModel = assetModelMapper.toEntity(assetModelDto);

        Category category = getCategoryFromDto(assetModelDto);
        assetModel.setCategory(category);

        if (!assetModelDto.getSpecDtos().isEmpty()) {
            List<AssetTechSpec> techSpecs = assetTechSpecMapper.toEntities(assetModelDto.getSpecDtos());
            assetTechSpecRepository.saveAll(techSpecs);
            assetModel.setTechSpecs(techSpecs);
        }

        if (assetModelDto.getParentAssetModelId() != null) {
            AssetModel parentAssetModel = assetModelRepository.findById(assetModelDto.getParentAssetModelId())
                    .orElseThrow(() -> new UserException("Wrong parentAsset id passed!"));
            assetModel.setParentModel(parentAssetModel);
        }

        assetModel.setCreatedBy(getCurrentUserAsEntity());

        return assetModelMapper.toDto(assetModelRepository.save(assetModel));
    }

    public void partialUpdate(Long id, AssetModelDto assetModelDto) {
        String operationLog ="Request to partially update assetModel : %s".formatted(assetModelDto.toString());
        logCurrentUser(operationLog);

        AssetModel assetModel = assetModelRepository.findById(id)
                .orElseThrow(() -> new UserException("AssetModel not found", 404));

        updateCategoryFromDto(assetModel, assetModelDto);

        assetModelMapper.partialUpdate(assetModel, assetModelDto);

        assetModelRepository.save(assetModel);
    }

    public void deactivate(Long id) {
        String operationLog ="Request to deactivate assetModel with id: %s".formatted(id);
        logCurrentUser(operationLog);

        assetModelRepository.deleteById(id);
    }

    public List<AssetModelDto> getAllActive() {
        return assetModelMapper.toDtos(assetModelRepository.findAllActive());
    }

    private Category getCategoryFromDto(AssetModelDto assetModelDto) {
        Long subcategoryId = (assetModelDto.getSubcategory2Id() != null)
                ? assetModelDto.getSubcategory2Id() : assetModelDto.getSubcategory1Id();
        return categoryRepository.findById(subcategoryId)
                .orElseThrow(() -> new UserException("Wrong subcategory id passed!"));
    }

    private void updateCategoryFromDto(AssetModel assetModel, AssetModelDto assetModelDto) {
        Long subcategoryId = (assetModelDto.getSubcategory2Id() != null)
                ? assetModelDto.getSubcategory2Id()
                : assetModelDto.getSubcategory1Id();

        if (subcategoryId != null) {
            Category category = categoryRepository.findById(subcategoryId)
                    .orElseThrow(() -> new RuntimeException("Wrong subcategory id passed"));
            assetModel.setCategory(category);
        }
    }
}
