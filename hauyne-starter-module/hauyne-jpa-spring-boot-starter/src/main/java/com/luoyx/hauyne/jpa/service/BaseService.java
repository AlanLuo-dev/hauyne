package com.luoyx.hauyne.jpa.service;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;


/**
 * 基础服务接口
 * <p>
 * 该接口为基础服务接口，定义了一些通用的 CRUD 操作方法，用于对实体对象进行持久化操作。
 * 该接口提供了基本的查询和持久化操作，可供各个领域模型的服务接口进行继承和实现。
 *
 * @param <T>  表示实体对象的类型
 * @param <ID> 表示实体对象的主键类型
 * @author Alan.Luo
 * @since 2023/4/10 21:16
 */
public interface BaseService<T, ID> {

    /**
     * 获取实体类的Class对象
     *
     * @return 实体类的Class对象
     */
    Class<T> getDomainClass();

    /**
     * 保存实体对象
     *
     * @param entity 实体对象
     * @param <S>    实体类型
     * @return 保存后的实体对象
     */
    <S extends T> S save(S entity);

    /**
     * 批量保存实体对象
     *
     * @param entities 实体对象列表
     * @param <S>      实体类型
     * @return 保存后的实体对象列表
     */
    <S extends T> List<S> saveAll(Iterable<S> entities);

    /**
     * 根据实体对象进行删除
     *
     * @param entity 实体对象
     */
    void delete(T entity);

    /**
     * 根据主键进行删除
     *
     * @param id 主键
     */
    void deleteById(ID id);

    /**
     * 删除所有实体对象
     */
    void deleteAll();

    /**
     * 按顺序逐个删除传入的实体对象
     *
     * @param entities 实体对象列表
     */
    void deleteAll(Iterable<? extends T> entities);

    /**
     * 一次性批量删除传入的实体对象
     * <p>
     * 可以是实体对象的集合、列表等等。
     * 批量删除通常比逐个删除更快，但由于需要将多个对象同时加载到持久化上下文中，因此在删除大量数据时，可能会导致内存消耗过高。
     *
     * @param entities 实体对象列表
     */
    void deleteInBatch(Iterable<T> entities);

    /**
     * 批量删除指定实体的所有记录。
     * <p>
     * 与 deleteAll 方法不同的是，该方法会将所有记录一次性提交到数据库，而不是一个一个地进行提交。
     * 这可以大大提高删除效率，但也可能会对性能造成一些负面影响。
     */
    void deleteAllInBatch();

    /**
     * 根据主键查询单个实体对象
     *
     * @param id 主键
     * @return 查询到的实体对象，如果不存在则返回null
     */
    T getOne(ID id);

    /**
     * 根据示例查询条件查询单个实体对象
     *
     * @param example 示例查询条件
     * @param <S>     实体类型
     * @return 查询到的实体对象，如果不存在则返回null
     */
    <S extends T> Optional<S> findOne(Example<S> example);

    /**
     * 根据主键查询单个实体对象
     *
     * @param id 主键
     * @return 查询到的实体对象，如果不存在则返回null
     */
    Optional<T> findById(ID id);

    /**
     * 查询所有实体对象
     *
     * @return 实体对象列表
     */
    List<T> findAll();


    /**
     * 查询所有实体
     *
     * @param sort 排序条件
     * @return 符合排序条件的实体列表
     */
    List<T> findAll(Sort sort);

    /**
     * 分页查询实体
     *
     * @param pageable 分页条件
     * @return 符合分页条件的实体列表
     */
    Page<T> findAll(Pageable pageable);


    /**
     * 示例查询
     *
     * @param example 查询条件
     * @param <S>     查询结果类型
     * @return 符合条件的查询结果列表
     */
    <S extends T> List<S> findAll(Example<S> example);

    /**
     * 示例查询
     *
     * @param example 查询条件
     * @param sort    排序条件
     * @param <S>     查询结果类型
     * @return 符合条件的查询结果列表
     */
    <S extends T> List<S> findAll(Example<S> example, Sort sort);

    /**
     * 示例查询
     *
     * @param example  查询条件
     * @param pageable 分页条件
     * @param <S>      查询结果类型
     * @return 符合条件的查询结果分页信息
     */
    <S extends T> Page<S> findAll(Example<S> example, Pageable pageable);

    /**
     * 根据ID列表查询实体列表
     *
     * @param ids ID列表
     * @return 符合ID列表的实体列表
     */
    List<T> findAllById(Iterable<ID> ids);

    /**
     * 统计实体总数
     *
     * @return 实体总数
     */
    long count();

    /**
     * 根据查询条件统计实体数量
     *
     * @param example 查询条件
     * @param <S>     查询结果类型
     * @return 符合查询条件的实体数量
     */
    <S extends T> long count(Example<S> example);

    /**
     * 根据查询条件判断实体是否存在
     *
     * @param example 查询条件
     * @param <S>     查询结果类型
     * @return 如果实体存在返回true，否则返回false
     */
    <S extends T> boolean exists(Example<S> example);

    /**
     * 根据ID判断实体是否存在
     *
     * @param id 实体ID
     * @return 如果实体存在返回true，否则返回false
     */
    boolean existsById(ID id);

    /**
     * 立即刷新持久化上下文
     */
    void flush();

    /**
     * 保存实体并立即刷新持久化上下文
     *
     * @param entity 待保存的实体
     * @param <S>    实体类型
     * @return 保存后的实体
     */
    <S extends T> S saveAndFlush(S entity);


//
////    Page<T> findAll(Pageable pageable);
//
//    /**
//     * 分页 使用偏移量
//     *
//     * @param query
//     * @param <U>
//     * @param <Q>
//     * @return
//     */
////    <U, Q extends PrimeNgPageQuery> PageResult<U> pageList(Q query);
}
