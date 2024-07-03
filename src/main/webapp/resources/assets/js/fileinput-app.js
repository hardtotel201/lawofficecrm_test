
// 初始化上传组件(一般文件上传,不支持预览)
function initFileInput(maxFileCount, fileNames, fileExtensions,uploadAsync) {
    var locats = (window.location + '').split('/');
    var strUrl=locats[0] + '//' + locats[2] + '/' + locats[3]+"/upload";
    var downloadUrls=new Array();
    for(var index in fileNames){
		downloadUrls[index]=locats[0] + '//' + locats[2] + '/' + locats[3]+"/upload/"+fileNames[index];
		console.log("initFileInput downloadUrils:"+downloadUrls[index]);
    }
	var setting = {
		language : 'zh',// 设置语言
		uploadUrl :strUrl,
		uploadAsync : uploadAsync,  
		showUpload  : false, // 是否显示上传按钮
		showCaption : true,  // 是否显示标题按钮
		showRemove  : true,  // 是否显示删除按钮
		showBrowse  : true,  // 是否显示预览按钮
		showPreview : true,  // 是否显示预览按区域
		initialPreviewShowDelete : true,
        initialPreview: downloadUrls,
        initialPreviewAsData: true,
        overwriteInitial: true,
		showCancel : true,
		browseOnZoneClick : true,
		dropZoneEnabled : false, // 是否显示拖拽区域
		maxFileCount : maxFileCount, // 表示允许同时上传的最大文件个数
		msgFilesTooMany : "选择上传的文件数量 超过允许的最大数值！",
		previewFileIcon : '<i class="glyphicon glyphicon-file"></i>',
		preferIconicPreview : true,
		layoutTemplates : {
		    actionUpload : '',
		},
		fileActionSettings: {                               // 在预览窗口中为新选择的文件缩略图设置文件操作的对象配置
             showRemove: false,                             // 显示删除按钮
             showUpload: false,                             // 显示上传按钮
             showDownload: false,                            // 显示下载按钮 （这个也很重要）
             showZoom: true,                                // 显示缩放按钮
             showDrag: false,                               // 显示拖拽
             removeIcon: '<i class="fa fa-trash"></i>',      // 删除图标
             uploadIcon: '<i class="fa fa-upload"></i>',     // 上传图标
             uploadRetryIcon: '<i class="fa fa-repeat"></i>'  // 重试图标
         }
    };
    if (fileExtensions != null)
		allowedFileExtensions = fileExtensions; // 接收文件后缀
    $("#fileInputControl").fileinput('destroy');
    $("#fileInputControl").fileinput(setting);
}

//上传一个文件
function initFileInputOne(fileNames){
    var fileExtensions=['image', 'html', 'text', 'video', 'audio', 'flash', 'object','png','jpg','db','txt','pdf'];
	initFileInput(1,fileNames,fileExtensions,false);
}


//初始化上传组件(图像文件上传，带预览功能)
function initFileInputForImage(fileNames,uploadAsync) {
    var locats = (window.location + '').split('/');
    var strUrl=locats[0] + '//' + locats[2] + '/' + locats[3]+"/upload";
    var imgUrls=new Array();
    for(var index in fileNames)
	imgUrls[index]=locats[0] + '//' + locats[2] + '/' + locats[3]+"/upload/"+fileNames[index];
    $("#fileInputControl").fileinput('destroy');
    $("#fileInputControl").fileinput({
		language : 'zh',// 设置语言
		uploadUrl :strUrl,
		uploadAsync : uploadAsync,
		showBrowse : true,
		browseLabel:'选择文件',
		showCaption : true,
		showUpload : false,
		showRemove : false,
		showPreview : true,
		showCancel : true,
		showClose:    false,
		autoReplace:true,
		browseOnZoneClick : true,
		dropZoneEnabled: false,//是否显示拖拽区域
		minFileCount: 1,                                        // 最小上传数量
	    maxFileCount: fileNames.length,                         // 最大上传数量
		initialPreviewShowDelete : true,
	    initialPreview: imgUrls,
	    initialPreviewAsData: true,
	    overwriteInitial: true,
	    allowedPreviewTypes:["image"],
	    allowedFileExtensions:["jpg","jpeg","png","bmp"],
	    previewFileIcon : '<i class="glyphicon glyphicon-file"></i>',
	    layoutTemplates :{
	        actionUpload:'',//去除上传预览缩略图中的上传图片；
	        indicator:'',
	        caption:'',
	        size:'',
	    },
    });
    $("#fileInputControl").fileinput('_initFileActions');//这行代码就是调用绑定删除事件的
}
