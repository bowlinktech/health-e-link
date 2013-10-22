<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<aside class="secondary">
	<nav class="secondary-nav" role="navigation">
		<ul class="nav nav-pills nav-stacked">
			<li ${param['page'] == 'details' ? 'class="active"' : ''}><a href="${param['page'] != 'details' ? './' : 'javascript:void(0);'}" title="Step 1: Message Type Details"><span class="badge">1</span> Details</a></li>
			<li ${param['page'] == 'mappings' ? 'class="active"' : ''} ${id > 0 ? '' : 'class="disabled"'}><a href="${param['page'] != 'mappings' && id > 0 ? 'mappings' : 'javascript:void(0);'}" title="Step 2: Message Type Field Mappings"><span class="badge">2</span> Field Mappings</a></li>
			<li ${param['page'] == 'translations' ? 'class="active"' : ''} ${id > 0 ? '' : 'class="disabled"'}><a href="${param['page'] != 'translations' && id > 0 ? 'translations' : 'javascript:void(0);'}" title="Step 3: Message Type Data Translations"><span class="badge">3</span> Data Translations</a></li>
		</ul>
	</nav>
</aside>



