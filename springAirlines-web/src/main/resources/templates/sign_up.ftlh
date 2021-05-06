<#ftl encoding="UTF-8"/>
<#import "layouts/base.ftlh" as base>
<#import "spring.ftl" as spring/>
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
                                <@spring.bind "signUpForm"/>
                                <form action="/signUp" autocomplete="off" method="post" id="form">
                                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                                    <div class="form-group">
                                        <span class="text-black-50">Email</span>
                                        <@spring.formInput "signUpForm.email" "class='form-control' required"/>
                                        <@spring.showErrors "<br>" "error"/>
                                    </div>
                                    <div class="form-group">
                                        <span class="text-black-50">Пароль</span>
                                        <@spring.formPasswordInput "signUpForm.password" "class='form-control'                                          required "/>
                                        <@spring.showErrors "<br>" "error"/>
                                        <#if passwordsErrorMessage??><p class="error">${passwordsErrorMessage}</p></#if>
                                    </div>
                                    <div class="form-group">
                                        <span class="text-black-50">Повторите пароль</span>
                                        <@spring.formPasswordInput "signUpForm.passwordAgain" "class='form-control'                                          required"/>
                                        <#if passwordsErrorMessage??><p class="error">${passwordsErrorMessage}</p></#if>
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