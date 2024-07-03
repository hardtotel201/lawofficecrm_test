#include "${classNameLower}service.h"
#include <QDebug>
#include <QSqlError>
#include "global/appvar.h"
#include "global/appconst.h"
#include "util/mysqlutils.h"

using namespace util;
using namespace global;
using namespace service::${parentCategory};

/**分页查询
 * @brief findAllPaged
 * @param pageSize
 * @param pageIndex
 * @return
 */
PageInfo ${className}Service::findAllPaged(int pageSize,int pageIndex){
    //1-生成分页对象
    PageInfo page;
    page.rowTotal=this->count();
    page.pageSize=pageSize;
    int pageCount=page.rowTotal%page.pageSize==0?page.rowTotal/page.pageSize
                                               :page.rowTotal/page.pageSize+1;
    page.pageIndex=pageIndex<=(pageCount-1)?pageIndex:pageCount-1;
    int offset= page.pageSize*page.pageIndex;
    QString sql="select ${className}.* from ${className} limit "+QString::number(page.pageSize)+" offset "+QString::number(offset);
    QSqlQuery query(MySQLUtils::getConnection());
    bool ret=query.exec(sql);
    if(ret==true){
         QList<${className}> list=roMapping(query);
         page.dataList.setValue(list);
    }
    return page;
}

//查询数量
int ${className}Service::count(){
    QSqlQuery query(MySQLUtils::getConnection());
    bool ret=query.exec("select count(${indexName}) from ${className}");
    if(ret==true){
         if(query.next())
             return query.value(0).toUInt();
    }
    return -1;
}

 //根据标识查询
${className} ${className}Service::find(int ${indexName}){
    ${className} obj;
    QString sql="select ${className}.*  from ${className} where ${className}.${indexName}="+QString::number(${indexName});
    QSqlQuery query(MySQLUtils::getConnection());
    bool ret=query.exec(sql);
    if(ret==true){
         QList<${className}> objs=roMapping(query);
         if(objs.size()>0)
             obj= objs[0];
    }
    else
        qDebug()<<"${className}Service::find() error:"<<sql;
    return obj;
}

//查询所有(数据量大谨慎使用)
QList<${className}> ${className}Service::findAll(){
    QList<${className}> objs;
    QSqlQuery query(MySQLUtils::getConnection());
    bool ret=query.exec("select * from ${className}");
    if(ret==true){
         objs=roMapping(query);
    }
    return objs;
}

//新增
QString ${className}Service::insert(${className} obj){
	return "";
}

//修改
QString ${className}Service::edit(${className} obj){
	return "";
}                      

//删除
QString ${className}Service::deleteById(int ${indexName}){
	QSqlQuery query(MySQLUtils::getConnection());
    QString str = QString("delete from ${className} where ${indexName}="+QString::number(${indexName}));
    bool ret = query.exec(str);
    if(ret==true)
        return "";
    else
        return "删除用户失败!";
}               

//关系-》对象映射
QList<${className}> ${className}Service::roMapping(QSqlQuery& query){
    QList<${className}>  list;
    while(query.next())
    {
        ${className} obj;
<#list fields as e>
	<#if (e.beanType=="int") >
		obj.${e.beanName}    =query.value("${e.beanName}").toInt();
	</#if>
	<#if (e.beanType=="double") >
		obj.${e.beanName}    =query.value("${e.beanName}").toDouble();
	</#if>
	<#if (e.beanType=="float") >
		obj.${e.beanName}    =query.value("${e.beanName}").toFloat();
	</#if>
	<#if (e.beanType=="QString") >
		obj.${e.beanName}    =query.value("${e.beanName}").toString();
	</#if>
	<#if (e.beanType=="QDateTime") >
		obj.${e.beanName}    =query.value("${e.beanName}").toDateTime();
	</#if>
</#list>
        list.append(obj);
    }
    return list;
}