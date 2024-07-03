<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="${mapperInterfacePath}.${modelClassName}Mapper">

  <select id="findAll" resultType="${modelClassName}">
    select * from ${modelClassName}
  </select>
  
  
  <select id="findAllPaged" parameterType="${modelClassName}" resultType="${modelClassName}">
    select ${modelClassName}.* from ${modelClassName}
    order by ${'${'}sortField${'}'} ${'${'}sortType${'}'}
  </select>
  
  <select id="findById" parameterType="Long" resultType="${modelClassName}">
    select * from ${modelClassName} where  ${indexName}=${'#{'}id${'}'}
  </select>
  
  <select id="findAllByField" parameterType="Map" resultType="${modelClassName}">
    select * from ${modelClassName} where ${'${'}fieldName${'}'}=${'#{'}fieldValue${'}'}
  </select>
  
  <insert id="insert" parameterType="${modelClassName}" flushCache="true" >
	  <selectKey resultType="long" order="BEFORE" keyProperty="${indexName}">
		SELECT seq${indexName}.NEXTVAL FROM DUAL
	  </selectKey>
      insert into ${modelClassName}(<#list fields as e>${e.beanName}<#if e_has_next>,</#if></#list>)
      values(<#list fields as e>${'#{'}${e.beanName},jdbcType=${e.myBatisType}${'}'}<#if e_has_next>,</#if></#list>)
  </insert>
  
   <update id="update" parameterType="${modelClassName}" >
     update ${modelClassName} 
     set <#list fields as e>${e.beanName}=${'#{'}${e.beanName},jdbcType=${e.myBatisType}${'}'}<#if e_has_next>,</#if></#list>
     where ${indexName}=${'#{'}${indexName}${'}'}
  </update>
  
  <delete id="deleteById" parameterType="long">
      delete from ${modelClassName} where ${indexName}=${'#{'}id${'}'}
  </delete>
  
</mapper>