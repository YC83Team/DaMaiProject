	//打开修改窗口
	function edit(index) {
		//禁用编号输入框
		$("#bookid").textbox({disabled:true});
		var rows = $('#dg').datagrid("getRows");
		$("#dd").dialog('open');
		$('#ff').form('load', rows[index]);
	}
	
	//修改
	function save(){
		//启用id输入框，否则无法序列化
		$("#bookid").textbox({disabled:false});
		//询问是否修改
		$.messager.confirm('Confirm', '您确定要修改吗?', function(r){
			if (r){
				$('#ff').form('submit',{
					url:"../modifybook.do",
					success:res=>{
						//alert(res);
						//原有的res==》消息，现在的res==》json对象（code，msg，data）
						$.messager.alert('tip',res);
						//关闭窗口
						$("#dd").dialog('close');
						//刷新表格
						 $('#dg').datagrid("reload");
					}
				});
			}
			
		});
		
	}
	
	//取消修改
	function cancel(){
		$("#dd").dialog('close');
	}