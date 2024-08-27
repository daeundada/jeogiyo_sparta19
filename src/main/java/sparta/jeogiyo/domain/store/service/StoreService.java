package sparta.jeogiyo.domain.store.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import sparta.jeogiyo.domain.store.dto.request.StoreSearchRequest;
import sparta.jeogiyo.domain.store.dto.request.StoreRequest;
import sparta.jeogiyo.domain.store.dto.response.StoreResponse;
import sparta.jeogiyo.domain.store.domain.Store;
import sparta.jeogiyo.domain.store.repository.StoreRepository;

@Service
public class StoreService {

    private final StoreRepository storeRepository;

    public StoreService(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    @Transactional
    public Store addStore(StoreRequest storeRequest) {
        return storeRepository.save(storeRequest.toEntity());
    }

    //get 메소드들의 경우 읽기 전용이기때문에 readOnly 로 성능 최적화를 ,,, 노려봅니다,,,,
    @Transactional(readOnly = true)
    public List<StoreResponse> findAll() {
        return storeRepository.findAll().stream()
                .map(Store::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Store findStore(UUID storeId) {
        return storeRepository.findById(storeId)
                .orElseThrow(
                        () -> new EntityNotFoundException("Store not found with id: " + storeId));
    }

    @Transactional
    public Store deleteStore(UUID storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(
                        () -> new EntityNotFoundException("Store not found with id: " + storeId));
        //삭제 요청시 내부로직은 patch로 동작해 상태만 '삭제' 상태로 변경됩니다.
        store.setIs_deleted(true);
        return storeRepository.save(store);
    }

    @Transactional
    public Store patchStore(UUID storeId, StoreRequest storeRequest) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(
                        () -> new EntityNotFoundException("Store not found with id: " + storeId));

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

    public Page<Store> searchStores(StoreSearchRequest request) {
        Pageable pageable = PageRequest.of(request.getPageNumber(), request.getPageSize(),
                Sort.by(Sort.Order.by(request.getSortBy())).ascending());

        Specification<Store> spec = buildSpecification(request);
        return storeRepository.findAll(spec, pageable);
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
}
