<#import "layouts/base.ftlh" as base>
<@base.main title="Trips" scripts=["autoComplete.js"]>
    <main>
        <div class="container">
            <h1>Trips</h1>
            <form method="post" action="/trip" autocomplete="off">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                <div class="form-row">
                    <div class="form-group col">
                        <label for="from">From</label>
                        <input id="from" class="form-control" name="from" value="<#if from??>${from}</#if>"/>
                    </div>
                    <div class="form-group col">
                        <label for="to">To</label>
                        <input id="to" class="form-control" name="to" value="<#if to??>${to}</#if>"/>
                    </div>
                    <button type="submit" class="btn btn-primary">Find</button>
                </div>
            </form>

            <#if cheapest?? && cheapest.isPresent()>
                <#assign c=cheapest.get()>
                <div class="card mt-5 mb-5">
                    <h5 class="card-header">Cheapest</h5>
                    <div class="card-body">
                        <h5 class="card-title">Time (minutes): ${c.time}, Cost: ${c.cost})</h5>
                        <p class="card-text">
                            <#list c.cities as cheapest_city>
                                ${cheapest_city.name} <#if cheapest_city?has_next>-></#if>
                            </#list>
                        </p>
                        <a href="/booking/cheapest" class="btn btn-primary">Booking</a>
                    </div>
                </div>
            <#else>
                <div class="text-center">
                    <h2>Cannot find cheapest trip</h2>
                </div>
            </#if>
            <#if fastest?? && fastest.isPresent()>
                <#assign f=fastest.get()>
                <div class="card mt-5 mb-5">
                    <h5 class="card-header">Fastest</h5>
                    <div class="card-body">
                        <h5 class="card-title">Time (minutes): ${f.time}, Cost: ${f.cost})</h5>
                        <p class="card-text">
                            <#list f.cities as fastest_city>
                                ${fastest_city.name} <#if fastest_city?has_next>-></#if>
                            </#list>
                        </p>
                        <a href="/booking/fastest" class="btn btn-primary">Booking</a>
                    </div>
                </div>
            <#else>
                <div class="text-center">
                    <h2>Cannot find fastest trip</h2>
                </div>
            </#if>
            <#if direct?? && direct.isPresent()>
                <#assign d=direct.get()>
                <div class="card mt-5 mb-5">
                    <h5 class="card-header">Direct</h5>
                    <div class="card-body">
                        <h5 class="card-title">Time (minutes): ${d.time}, Cost: ${d.cost})</h5>
                        <p class="card-text">
                            <#list d.cities as direct_city>
                                ${direct_city.name} <#if direct_city?has_next>-></#if>
                            </#list>
                        </p>
                        <a href="/booking/direct" class="btn btn-primary">Booking</a>
                    </div>
                </div>
            <#else>
                <div class="text-center">
                    <h2>Cannot find direct trip</h2>
                </div>
            </#if>
        </div>
    </main>
</@base.main>

