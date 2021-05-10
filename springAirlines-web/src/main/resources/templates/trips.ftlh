<#import "layouts/base.ftlh" as base>
<@base.main title="Trips">
    <main>
        <div class="container">
            <h1>Trips</h1>
            <form method="post" action="/trip">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                <div class="form-row">
                    <div class="form-group col">
                        <label for="from">From</label>
                        <select id="from" class="form-control" name="from">
                            <#list cities as city>
                                <option value="${city.name}">${city.name}</option>
                            </#list>
                        </select>
                    </div>
                    <div class="form-group col">
                        <label for="to">To</label>
                        <select id="to" class="form-control" name="to">
                            <#list cities as city>
                                <option value="${city.name}">${city.name}</option>
                            </#list>
                        </select>
                    </div>
                    <button type="submit" class="btn btn-primary">Find</button>
                </div>
            </form>

            <#if cheapest??>
                <div class="card mt-5 mb-5">
                    <h5 class="card-header">Cheapest</h5>
                    <div class="card-body">
                        <h5 class="card-title">Time (minutes): ${cheapest.time}, Cost: ${cheapest.cost})</h5>
                        <p class="card-text">
                            <#list cheapest.cities as cheapest_city>
                                ${cheapest_city.name} <#if cheapest_city?has_next>-></#if>
                            </#list>
                        </p>
                        <a href="/booking/cheapest" class="btn btn-primary">Booking</a>
                    </div>
                </div>
            </#if>
            <#if fastest??>
                <div class="card mt-5 mb-5">
                    <h5 class="card-header">Fastest</h5>
                    <div class="card-body">
                        <h5 class="card-title">Time (minutes): ${fastest.time}, Cost: ${fastest.cost})</h5>
                        <p class="card-text">
                            <#list fastest.cities as fastest_city>
                                ${fastest_city.name} <#if fastest_city?has_next>-></#if>
                            </#list>
                        </p>
                        <a href="/booking/fastest" class="btn btn-primary">Booking</a>
                    </div>
                </div>
            </#if>
            <#if direct??>
                <div class="card mt-5 mb-5">
                    <h5 class="card-header">Direct</h5>
                    <div class="card-body">
                        <h5 class="card-title">Time (minutes): ${direct.time}, Cost: ${direct.cost})</h5>
                        <p class="card-text">
                            <#list direct.cities as direct_city>
                                ${direct_city.name} <#if direct_city?has_next>-></#if>
                            </#list>
                        </p>
                        <a href="/booking/direct" class="btn btn-primary">Booking</a>
                    </div>
                </div>
            </#if>
        </div>
    </main>
</@base.main>

