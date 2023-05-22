package com.acme.biz.data.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @Description: Spring Data Commons 会帮助接口生成动态代理对象 实现 Repository 透明化 （SQL、NoSQL）
 *               Spring Data Commons 支持自定义存储引擎
 * @Author <a href="mailto:zfy_zang@163.com">Vincent</a>
 * @Modify
 * @since
 * @see CrudRepository
 */
@Repository
public interface UserRepository extends CrudRepository {
}
