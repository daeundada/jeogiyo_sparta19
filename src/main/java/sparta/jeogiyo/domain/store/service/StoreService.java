package sparta.jeogiyo.domain.store.service;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;

import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import sparta.jeogiyo.domain.store.dto.request.StoreSearchRequest;
import sparta.jeogiyo.domain.store.dto.request.StoreRequest;
import sparta.jeogiyo.domain.store.dto.response.StoreResponse;
import sparta.jeogiyo.domain.store.domain.Store;
import sparta.jeogiyo.domain.store.repository.StoreRepository;
import sparta.jeogiyo.domain.user.UserDetailsImpl;
import sparta.jeogiyo.global.response.CustomException;
import sparta.jeogiyo.global.response.ErrorCode;

@Slf4j
@Service
public class StoreService {

    private final StoreRepository storeRepository;

    public StoreService(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    @Transactional
    public Store addStore(StoreRequest storeRequest, UserDetailsImpl userDetails) {

        validateAddStore(storeRequest);

        return storeRepository.save(storeRequest.toEntity(userDetails));
    }

    //get 메소드들의 경우 읽기 전용이기때문에 readOnly 로 성능 최적화를 ,,, 노려봅니다,,,,
    @Transactional(readOnly = true)
    @Cacheable(value = "storePage", key = "T(String).format('%d_%d_%s_%b', #page, #size, #sortBy, #isAsc)")
    public Page<StoreResponse> findAll(int page, int size, String sortBy, boolean isAsc) {
        if (size != 10 && size != 30 && size != 50) {
            size = 10;
        }

        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Store> storePage = storeRepository.findAll(pageable);
        return storePage.map(Store::toResponse);
    }

    @Transactional(readOnly = true)
    public Store findStore(UUID storeId) {
        return storeRepository.findById(storeId)
                .orElseThrow(
                        () -> new CustomException(ErrorCode.STORE_ID_NOT_FOUND));
    }

    @Transactional
    @CacheEvict(value = "storePage", allEntries = true)
    public Store deleteStore(UUID storeId, UserDetailsImpl user) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(
                        () -> new CustomException(ErrorCode.STORE_ID_NOT_FOUND));
        store.delete(user);
        return storeRepository.save(store);
    }

    @Transactional
    @CacheEvict(value = "storePage", allEntries = true)
    public Store updateStore(UUID storeId, StoreRequest storeRequest) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(
                        () -> new CustomException(ErrorCode.STORE_ID_NOT_FOUND));

        if (storeRequest.getStoreName() != null) {
            store.setStoreName(storeRequest.getStoreName());
        }
        if (storeRequest.getStoreNumber() != null) {
            store.setStoreNumber(storeRequest.getStoreNumber());
        }
        if (storeRequest.getCategory() != null) {
            store.setCategory(storeRequest.getCategory());
        }
        return storeRepository.save(store);
    }

    @Transactional(readOnly = true)
    public Page<StoreResponse> searchStores(StoreSearchRequest request, int page, int size,
            String sortBy, boolean isAsc) {

        if (size != 10 && size != 30 && size != 50) {
            size = 10;
        }

        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);

        Pageable pageable = PageRequest.of(page, size, sort);

        Specification<Store> spec = buildSpecification(request);

        Page<Store> storePage = storeRepository.findAll(spec, pageable);

        return storePage.map(Store::toResponse);
    }

    private Specification<Store> buildSpecification(StoreSearchRequest request) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (request.getStoreName() != null) {
                predicates.add(
                        criteriaBuilder.like(root.get("name"), "%" + request.getStoreName() + "%"));
            }

            if (request.getStoreNumber() != null) {
                predicates.add(
                        criteriaBuilder.equal(root.get("number"), request.getStoreNumber()));
            }

            if (request.getCategory() != null) {
                predicates.add(criteriaBuilder.equal(root.get("category"), request.getCategory()));
            }

            query.where(predicates.toArray(new Predicate[0]));

            return query.getRestriction();
        };
    }

    private void validateAddStore(StoreRequest request) {
        Optional<Store> checkStoreNumber = storeRepository.findBystoreNumber(
                request.getStoreNumber());
        if (checkStoreNumber.isPresent()) {
            log.warn("가게등록 실패 - 중복된 전화번호: {}", request.getStoreNumber());
            throw new CustomException(ErrorCode.DUPLICATE_STORE_NUMBER);
        }

        Optional<Store> checkStoreName = storeRepository.findBystoreName(request.getStoreName());
        if (checkStoreName.isPresent()) {
            log.warn("가게등록 실패 - 중복된 가게이름: {}", request.getStoreName());
            throw new CustomException(ErrorCode.DUPLICATE_STORE_NAME);
        }
    }

    public UUID findByUserId(UserDetailsImpl userDetails) {

        Long user = userDetails.getUser().getUserId();

        return storeRepository.findStoreByUser_UserId(user).getStoreId();
    }
}
