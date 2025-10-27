package com.luoyx.hauyne.jpa.service.impl;

import com.luoyx.hauyne.jpa.repository.BaseRepository;
import com.luoyx.hauyne.jpa.service.BaseService;
import com.luoyx.hauyne.jpa.util.GenericsUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 基础服务实现类，封装了通用的增删改查操作，实现了 {@link BaseService} 接口。
 *
 * @param <T>  实体类型
 * @param <ID> 实体主键类型
 * @param <R>  对应实体的 {@link JpaRepository} 对象类型
 * @author Alan.Luo
 * @since 2023/4/10 21:18
 */
public class BaseServiceImpl<T, ID, R extends BaseRepository<T, ID>> implements BaseService<T, ID> {

    private static final Map<Class, Class> DOMAIN_CLASS_CACHE = new ConcurrentHashMap<>();

    protected final R repository;

    public BaseServiceImpl(R repository) {
        this.repository = repository;
    }

    /**
     * 获取实体类的Class对象
     *
     * @return 实体类的Class对象
     */
    @Override
    public Class<T> getDomainClass() {
        Class<?> thisClass = getClass();
        Class<T> domainClass = DOMAIN_CLASS_CACHE.get(thisClass);
        if (Objects.isNull(domainClass)) {
            domainClass = GenericsUtils.getGenericClass(thisClass, 0);
            DOMAIN_CLASS_CACHE.putIfAbsent(thisClass, domainClass);
        }

        return domainClass;
    }

    /**
     * 保存实体对象
     *
     * @param entity 实体对象
     * @return 保存后的实体对象
     */
    @Override
    public <S extends T> S save(S entity) {
        return repository.save(entity);
    }

    /**
     * 批量保存实体对象
     *
     * @param entities 实体对象列表
     * @return 保存后的实体对象列表
     */
    @Override
    public <S extends T> List<S> saveAll(Iterable<S> entities) {
        return repository.saveAll(entities);
    }

    /**
     * 根据实体对象进行删除
     *
     * @param entity 实体对象
     */
    @Override
    public void delete(T entity) {
        repository.delete(entity);
    }

    /**
     * 根据主键进行删除
     *
     * @param id 主键
     */
    @Override
    public void deleteById(ID id) {
        repository.deleteById(id);
    }

    /**
     * 删除所有实体对象
     */
    @Override
    public void deleteAll() {
        repository.deleteAll();
    }

    /**
     * 按顺序逐个删除传入的实体对象
     *
     * @param entities 实体对象列表
     */
    @Override
    public void deleteAll(Iterable<? extends T> entities) {
        repository.deleteAll(entities);
    }

    /**
     * 一次性批量删除传入的实体对象
     * <p>
     * 可以是实体对象的集合、列表等等。
     * 批量删除通常比逐个删除更快，但由于需要将多个对象同时加载到持久化上下文中，因此在删除大量数据时，可能会导致内存消耗过高。
     *
     * @param entities 实体对象列表
     */
    @Override
    public void deleteInBatch(Iterable<T> entities) {
        repository.deleteInBatch(entities);
    }

    /**
     * 批量删除指定实体的所有记录。
     * <p>
     * 与 deleteAll 方法不同的是，该方法会将所有记录一次性提交到数据库，而不是一个一个地进行提交。
     * 这可以大大提高删除效率，但也可能会对性能造成一些负面影响。
     */
    @Override
    public void deleteAllInBatch() {
        repository.deleteAllInBatch();
    }

    /**
     * 根据主键查询单个实体对象
     *
     * @param id 主键
     * @return 查询到的实体对象，如果不存在则返回null
     */
    @Override
    public T getOne(ID id) {
        return repository.getOne(id);
    }

    /**
     * 根据示例查询条件查询单个实体对象
     *
     * @param example 示例查询条件
     * @return 查询到的实体对象，如果不存在则返回null
     */
    @Override
    public <S extends T> Optional<S> findOne(Example<S> example) {
        return repository.findOne(example);
    }

    /**
     * 根据主键查询单个实体对象
     *
     * @param id 主键
     * @return 查询到的实体对象，如果不存在则返回null
     */
    @Override
    public Optional<T> findById(ID id) {
        return repository.findById(id);
    }

    /**
     * 查询所有实体对象
     *
     * @return 实体对象列表
     */
    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

    /**
     * 查询所有实体
     *
     * @param sort 排序条件
     * @return 符合排序条件的实体列表
     */
    @Override
    public List<T> findAll(Sort sort) {
        return repository.findAll(sort);
    }

    /**
     * 分页查询实体
     *
     * @param pageable 分页条件
     * @return 符合分页条件的实体列表
     */
    @Override
    public Page<T> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    /**
     * 示例查询
     *
     * @param example 查询条件
     * @return 符合条件的查询结果列表
     */
    @Override
    public <S extends T> List<S> findAll(Example<S> example) {
        return repository.findAll(example);
    }

    /**
     * 示例查询
     *
     * @param example 查询条件
     * @param sort    排序条件
     * @return 符合条件的查询结果列表
     */
    @Override
    public <S extends T> List<S> findAll(Example<S> example, Sort sort) {
        return repository.findAll(example, sort);
    }

    /**
     * 示例查询
     *
     * @param example  查询条件
     * @param pageable 分页条件
     * @return 符合条件的查询结果分页信息
     */
    @Override
    public <S extends T> Page<S> findAll(Example<S> example, Pageable pageable) {
        return repository.findAll(example, pageable);
    }

    /**
     * 根据ID列表查询实体列表
     *
     * @param ids ID列表
     * @return 符合ID列表的实体列表
     */
    @Override
    public List<T> findAllById(Iterable<ID> ids) {
        return repository.findAllById(ids);
    }

    /**
     * 统计实体总数
     *
     * @return 实体总数
     */
    @Override
    public long count() {
        return repository.count();
    }

    /**
     * 根据查询条件统计实体数量
     *
     * @param example 查询条件
     * @return 符合查询条件的实体数量
     */
    @Override
    public <S extends T> long count(Example<S> example) {
        return repository.count(example);
    }

    /**
     * 根据查询条件判断实体是否存在
     *
     * @param example 查询条件
     * @return 如果实体存在返回true，否则返回false
     */
    @Override
    public <S extends T> boolean exists(Example<S> example) {
        return repository.exists(example);
    }

    /**
     * 根据ID判断实体是否存在
     *
     * @param id 实体ID
     * @return 如果实体存在返回true，否则返回false
     */
    @Override
    public boolean existsById(ID id) {
        return repository.existsById(id);
    }

    /**
     * 立即刷新持久化上下文
     */
    @Override
    public void flush() {
        repository.flush();
    }

    /**
     * 保存实体并立即刷新持久化上下文
     *
     * @param entity 待保存的实体
     * @return 保存后的实体
     */
    @Override
    public <S extends T> S saveAndFlush(S entity) {
        return repository.saveAndFlush(entity);
    }

    //    @Override
//    public Page<T> findAll(Pageable pageable) {
//        return repository.findAll(pageable);
//    }

//    @Override
//    public <U, Q extends PrimeNgPageQuery> PageResult<U> pageList(Q query) {
//        Pageable pageable = new OffsetPageable(query.getFirst(), query.getRows(), toSort(query.getSortOrder(), query.getSortField()));
//        Page<T> page = findAll(pageable);
//
//        PageResult<U> pageResult = new PageResult<U>();
//        pageResult.setTotal(page.getTotalElements());
//        pageResult.setRows(page.getContent());
//        pageResult.setPageNum(page.getNumber());
//        pageResult.setSize(page.getSize());
//        pageResult.setPages(page.getTotalPages());
//        pageResult.setSort(page.getSort().toString());
//        pageResult.setHasPre(page.hasPrevious());
//        pageResult.setHasNext(page.hasNext());
//
//        return pageResult;
//    }

//    /**
//     * 将 PrimeNgPageQuery 中的排序字段和排序方式转换为 Sort 对象
//     *
//     * @return Sort 对象
//     */
//    public Sort toSort(Integer sortOrder, String sortField) {
//        Sort.Direction direction = (sortOrder != null && sortOrder == 1) ? Sort.Direction.ASC : Sort.Direction.DESC;
//        if (sortField != null) {
//            return Sort.by(direction, sortField);
//        } else {
//            return Sort.unsorted();
//        }
//    }

}
