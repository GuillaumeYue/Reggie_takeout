// i18n-init.js
(async () => {
    // 1. 加载 Element UI 多语言包（动态方式）
    const element = await import('../../plugins/element-ui/index.js');
    const lang = await import('../../plugins/element-ui/lang/en.js');
    element.default.locale(lang.default);

    // 2. 调用 Google 翻译逻辑（提前在页面中引入 google_translate.js）
    window.addEventListener('load', () => {
        setTimeout(() => {
            if (typeof translatePage === 'function') {
                translatePage('en');
            }
        }, 300);
    });
})();
