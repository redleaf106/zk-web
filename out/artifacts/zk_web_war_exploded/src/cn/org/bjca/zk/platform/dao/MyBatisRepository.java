package cn.org.bjca.zk.platform.dao;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/***************************************************************************
 * <pre>标识MyBatis的DAO,方便{@link org.mybatis.spring.mapper.MapperScannerConfigurer}的扫描。</pre>
 * @文件名称:  MyBatisRepository.java
 * @包   路   径：  cn.org.bjca.zk.platform.dao
 * @版权所有：北京数字认证股份有限公司 (C) 2012
 *
 * @类描述:  
 * @版本: V1.0
 * @创建人： wangwenc
 * @创建时间：2013-2-9 下午12:12:42
 *
 *
 *
 * @修改记录：
   -----------------------------------------------------------------------------------------------
             时间                      |       修改人            |         修改的方法                       |         修改描述                                                                
   -----------------------------------------------------------------------------------------------
                 |                 |                           |                                       
   ----------------------------------------------------------------------------------------------- 	
 
 ***************************************************************************/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface MyBatisRepository {
}
