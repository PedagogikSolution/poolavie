<!-- Contient la bar de navigation de l'interface principal -->
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<header class="w3-container w3-khaki w3-center">

		<ul class="w3-navbar w3-center w3-mobile">
			<li class="w3-xlarge"><a href="/Equipes?team=1&from=menu">${Pool.nomTeam1}</a></li>
		
			<li class="w3-xlarge"><a href="/Equipes?team=2&from=menu">${Pool.nomTeam2}</a></li>
		
			<li class="w3-xlarge"><a href="/Equipes?team=3&from=menu">${Pool.nomTeam3}</a></li>
		
			<li class="w3-xlarge"><a href="/Equipes?team=4&from=menu">${Pool.nomTeam4}</a></li>
		
			<li class="w3-xlarge"><a href="/Equipes?team=5&from=menu">${Pool.nomTeam5}</a></li>
		
			<li class="w3-xlarge"><a href="/Equipes?team=6&from=menu">${Pool.nomTeam6}</a></li>
		
			<li class="w3-xlarge"><a href="/Equipes?team=7&from=menu">${Pool.nomTeam7}</a></li>
			
			<li class="w3-xlarge"><a href="/Equipes?team=8&from=menu">${Pool.nomTeam8}</a></li>
			
			<c:if test="${Pool.nomTeam9!=null}">
			<li class="w3-xlarge"><a href="/Equipes?team=9&from=menu">${Pool.nomTeam9}</a></li>
			</c:if>		
			<c:if test="${Pool.nomTeam10!=null}">
			<li class="w3-xlarge"><a href="/Equipes?team=10&from=menu">${Pool.nomTeam10}</a></li>
			</c:if>
			<c:if test="${Pool.nomTeam11!=null}">
			<li class="w3-xlarge"><a href="/Equipes?team=11&from=menu">${Pool.nomTeam11}</a></li>
			</c:if>			
			<c:if test="${Pool.nomTeam12!=null}">
			<li class="w3-xlarge"><a href="/Equipes?team=12&from=menu">${Pool.nomTeam12}</a></li>
			</c:if>
			
		</ul>

		

</header>