${r'<?xml version="1.0" encoding="UTF-8"?>'}
${r'<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">'}
${r'<mapper namespace="需要替换成dao的全路径">'}

    ${r'<insert id="save"'}${r' parameterType="'}${classModel.uname!''}${r'DO" useGeneratedKeys="true" keyProperty="id">'}
        insert into ${classModel.tableName!''}
        (
        <#list propertyModelList as model>
            <#if model.columnName == "id">
            <#else>
            ${model.columnName!''}<#if model_has_next>,</#if>
            </#if>
        </#list>
        )values(
        <#list propertyModelList as model>
            <#if model.lname == "id">
            <#else>
                <#if model.lname == "createTime" || model.lname == "updateTime">now()<#if model_has_next>,</#if>
                <#else>
                 ${model.lname!''}<#if model_has_next>,</#if>
                </#if>
            </#if>
        </#list>
        )
    ${r'</insert>'}

    ${r'<insert id="saveBatch" parameterType="java.util.List">'}
        insert into ${classModel.tableName!''}
        (
        <#list propertyModelList as model>
            <#if model.columnName == "id">
            <#else>
            ${model.columnName!''}<#if model_has_next>,</#if>
            </#if>
        </#list>
        )values
        ${r'<foreach collection="list" item="item"  separator="," >'}
        (<#list propertyModelList as model>
            <#if model.lname == "id">
            <#else>
                <#if model.lname == "createTime" || model.lname == "updateTime">now()<#if model_has_next>,</#if>
                <#else>
                ${r'#{item.'}${model.lname!''}${r'}'}<#if model_has_next>,</#if>
                </#if>
            </#if>
        </#list> )
        ${r'</foreach>'}
    ${r'</insert>'}

    ${r'<delete id="delete"'}${r' parameterType="Long">'}
        delete from ${classModel.tableName!''} ${r'where id = #{id}'}
    ${r'</delete>'}

    ${r'<delete id="deleteByIds"'}${r' parameterType="java.util.List">'}
        delete from ${classModel.tableName!''} ${r'where id in('}
        ${r'<foreach collection="list" item="item"  separator="," >'}
            ${r'#{item}'}
        ${r'</foreach>)'}
    ${r'</delete>'}

    ${r'<update id="update"'}${r' parameterType="'}${classModel.uname!''}${r'DO">'}
        update
            ${classModel.tableName!''}
        set
        <#list propertyModelList as model>
            <#if model.columnName == "id">
            <#else>
            ${model.columnName!''} = ${r'#{'}${model.lname!''}${r'}'}<#if model_has_next>,</#if>
            </#if>
        </#list>
        ${r'where id = #{id}'}
    ${r'</update>'}

    ${r'<select id="findById"'}${r' parameterType="Long" resultType="'}${classModel.uname!''}${r'DO">'}
        SELECT
            ${r'<include refid="selectColumns"/>'}
        FROM
            ${classModel.tableName!''}
        ${r'where id = #{id}'}
    ${r'</select>'}

    ${r'<sql id="baseCondition" >'}
            WHERE 1=1
    ${r'</sql>'}

    ${r'<sql id="selectColumns" >'}
        <#list propertyModelList as model>
            ${model.columnName!''} as ${model.lname!''} <#if model_has_next>,</#if>
        </#list>
    ${r'</sql>'}

${r'</mapper>'}