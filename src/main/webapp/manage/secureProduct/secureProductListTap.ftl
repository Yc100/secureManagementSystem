
<script type="text/javascript" src="${basepath}/manage/manage.js"></script>
<style type="text/css">
.product-name {
	display: inline-block;
	width: 250px;
	overflow: hidden; /*注意不要写在最后了*/
	white-space: nowrap;
	-o-text-overflow: ellipsis;
	text-overflow: ellipsis;
}
</style>

	<form action="${basepath}/manage/product" namespace="/manage" method="post" theme="simple">
		<input type="hidden" value="${e.catalogID!""}" id="catalogID"/>
		<table class="table table-bordered table-condensed">
			<tr>
				<td style="text-align: right;">保险名称</td>
				<td style="text-align: left;">
				<input type="text"  value="${e.name!""}" name="name"  class="search-query input-small" id="name" />
				</td>
			</tr>
			<tr>
					<td colspan="20">
                    <#if checkPrivilege("secureProduct/selectListTap")>
						<button method="selectListTap" class="btn btn-primary" onclick="selectListTap(this)">
							<i class="icon-search icon-white"></i> 查询
						</button>
                    </#if>
					
					
        		    <#if checkPrivilege("user/bindUp")>
						<button method="bindUp" class="btn btn-warning" onclick="return submitIDs(this,'确定绑定选择的记录?');">
							<i class="icon-arrow-up icon-white"></i> 绑定
						</button>
                    </#if>

					
					<div style="float: right;vertical-align: middle;bottom: 0px;top: 10px;">
						<#include "/manage/system/pager.ftl"/>
					</div>
				</td>
			</tr>
		</table>

		<table class="table table-bordered table-condensed table-hover">
			<tr style="background-color: #dff0d8">
				<th width="20"><input type="checkbox" id="firstCheckbox" /></th>
				<th nowrap="nowrap">商品编号</th>
				
				<th>名称</th>
				<th>定价</th>
				<th>产品简介</th>
				<th>录入日期</th>
				<th>状态</th>
				<th>备注</th>
				<th width="60">操作</th>
			</tr>
            <#list pager.list as item>
				<tr>
					<td><input type="checkbox" name="id"
						value="${item.id!""}" /></td>
					<td nowrap="nowrap">&nbsp;${item.id!""}</td>	
					
					
					<td>&nbsp;${item.name!""}</td>
					<td>&nbsp;${item.price!""}</td>
					<td>&nbsp;${item.introduce!""}</td>
					<td>&nbsp;${item.createtime!""}</td>
	
			
					<td>&nbsp;
						<#if item.delete_flag??&&item.delete_flag==0>
							<img alt="已上架" src="${basepath}/resource/images/action_check.gif">
						
						<#elseif item.delete_flag??&&item.delete_flag==1>
							<img alt="已下架" src="${basepath}/resource/images/action_delete.gif">
						</#if>
					</td>
					
					<td>&nbsp;
						${item.remark!""}
					</td>
					<td >
					<a target="_blank" href="${systemSetting().www}/secureProduct/${item.id!""}.html" style="color:blue">查看</a>
					</td>
				</tr>
            </#list>

			<tr>
				<td colspan="70" style="text-align: center;">
                    <#include "/manage/system/pager.ftl"/></td>
			</tr>
		</table>
	</form>
	
<script type="text/javascript">
	$(function() {
		selectDefaultCatalog();
	});
	function selectDefaultCatalog(){
		var _catalogID = $("#catalogID").val();
		if(_catalogID!='' && _catalogID>0){
			$("#catalogSelect").attr("value",_catalogID);
		}
	}
</script>
