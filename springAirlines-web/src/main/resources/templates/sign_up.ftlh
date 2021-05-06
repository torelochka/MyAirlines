<#ftl encoding="UTF-8"/>
<#import "layouts/base.ftlh" as base>
<@base.main title="Регистрация">
    <main>
        <section class="mt-5 mb-4">
            <div class="row">
                <div class="col"></div>
                <div class="col-8 align-self-center">
                    <div class="card wish-list mb-4">
                        <div class="card">
                            <div class="card-body">
                                <h3 class="mb-4 text-black-80 mt-0 font-weight-bold">Регистрация</h3>
                                <#if error??>
                                    <div class="alert alert-danger" role="alert">
                                        ${error}
                                    </div>
                                </#if>
                                <h4 class="error-message" id="error-message"></h4>
                                <form action="/signUp" autocomplete="off" method="post" id="form">
                                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                                    <div class="form-group">
                                        <span class="text-black-50">Email</span>
                                        <input id="email-signup" type="email" data-verify="email"
                                               class="form-control" name="email"
                                               required>
                                    </div>
                                    <div class="form-group">
                                        <span class="text-black-50">Пароль</span>
                                        <input id="password-signup" type="password" class="form-control"
                                               name="password"
                                               pattern="(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z!@#$%^&*]{6,}"
                                               title="Password should consist of latin alphabet and must contains letter with capital case and at list one num"
                                               required>
                                    </div>
                                    <div class="form-group">
                                        <span class="text-black-50">Повторите пароль</span>
                                        <input id="password-rep-signup" type="password" class="form-control"
                                               name="passwordAgain"
                                               required>
                                    </div>
                                    <button class="btn btn-primary btn-lg btn-block waves-effect waves-light"
                                            id="submit">Войти
                                    </button>
                                </form>
                                <hr>
                                <p><a href="/signIn" class="text-secondary">Авторизация</a></p>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col"></div>
            </div>
        </section>
    </main>
</@base.main>