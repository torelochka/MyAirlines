<header class="header-section">
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <a class="navbar-brand" href="/main">Spring Airlines</a>
        <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
            <div class="navbar-nav">
                <a class="nav-item nav-link active" href="#">Главная</a>
                <#if !user??>
                    <a class="nav-item nav-link active" href="/signIn">Авторизация</a>
                    <a class="nav-item nav-link active" href="/signUp">Регистрация</a>
                    <#else>
                        <a class="nav-item nav-link active" href="/profile">Профиль</a>
                        <a class="nav-item nav-link active" href="/logout">Выйти</a>
                </#if>

            </div>
        </div>
    </nav>
</header>