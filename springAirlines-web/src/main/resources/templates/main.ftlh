<#import "layouts/base.ftlh" as base>
<@base.main title="Главная" scripts=["autoComplete.js"]>
    <main>
        <div class="container">
            <h3 id="tripsBlock"></h3>
            <form method="post" action="/trip" autocomplete="off">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                <div class="form-row">
                    <div class="form-group col">
                        <label for="from">From</label>
                        <input id="from" class="form-control" name="from"/>
                    </div>
                    <div class="form-group col">
                        <label for="to">To</label>
                        <input id="to" class="form-control" name="to"/>
                    </div>
                    <button type="submit" class="btn btn-primary">Find</button>
                </div>
            </form>
        </div>
    </main>
</@base.main>

