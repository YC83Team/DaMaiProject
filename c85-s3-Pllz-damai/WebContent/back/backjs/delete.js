	
var id=-1;  //全局变量，当使用右键菜单时，用来表示选中的列的id

//删除图书
function deleteb(){
	
	if(window.bookid<0){
		alert("please select a row before proceeding....");
	}else{
		
		$.messager.confirm('Confirm', '您确定要删除吗?', function(r){
			//取消
			if (!r){
				return;
			}
			//确定删除
			$('#menuform').form('submit',{
				url:"../product.do",
				onSubmit: function(param){
					param.id = window.id;
					param.op = 'delete';
			    },
				success:res=>{
					res=JSON.parse(res);
					$.messager.alert('tip',res.msg);
					//刷新表格
					$('#dg').datagrid("reload");
				}
			});
			
			
		});
				
	}
}
