<#import "layouts/base.ftlh" as base>
<@base.main title="Главная" >
    <main>
        <div class="container">
            <h1>Main</h1>
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
        </div>
    </main>
</@base.main>

