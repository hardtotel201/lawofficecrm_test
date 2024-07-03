#ifndef ${classNameUpper}_H
#define ${classNameUpper}_H
#include <QString>
#include <QObject>
#include <QDateTime>

namespace model {
    namespace ${parentCategory} {
        //${classComment}
        struct ${className}{
            //数据库字段
			<#list fields as e>
				<#if (e.beanName == indexName ) >
					${e.beanType}	${e.beanName}=-1;		//${e.beanTitle}
				</#if>
				<#if (e.beanType != indexName ) >
					${e.beanType}	${e.beanName};		    //${e.beanTitle}
			    </#if>
			</#list>
            //视图字段(数据列表)
        };
    }
}
Q_DECLARE_METATYPE(model::${parentCategory}::${className})
#endif // ${classNameUpper}_H