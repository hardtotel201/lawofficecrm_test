#ifndef ${classNameUpper}SERVICE_H
#define ${classNameUpper}SERVICE_H
#include <QList>
#include <QSqlQuery>
#include <QVariantList>
#include <service/base/baseservice.h>
#include "model/${parentCategory}/${classNameLower}.h"
#include "model/base/pageinfo.h"

using namespace model::base;
using namespace model::${parentCategory};
using namespace service::base;

namespace service {
    namespace ${parentCategory} {
        //${classComment}
        class ${className}Service : public BaseService
        {
            Q_OBJECT
        public:
            PageInfo findAllPaged(int pageSize,int pageIndex);  //分页查询
            ${className} find(int ${indexName});                    //根据标识查询
            QList<${className}> findAll();                      //查询所有(数据量大谨慎使用)
            QString insert(${className} obj);                   //新增
            QString edit(${className} obj);                     //修改
            QString deleteById(int ${indexName});               //删除
        private:
            QList<${className}> roMapping(QSqlQuery& query);    //关系-》对象映射
            int count();                                        //查询数量
        };
    }
}
#endif // ${classNameUpper}_H