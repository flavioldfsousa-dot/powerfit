const I18N = {
    lang: localStorage.getItem('zentrox_lang') || 'pt-BR',

    t(key) {
        const langData = translations[this.lang] || translations['pt-BR'];
        return langData[key] || translations['pt-BR'][key] || key;
    },

    setLang(code) {
        this.lang = code;
        localStorage.setItem('zentrox_lang', code);
        document.documentElement.lang = code;
        this.applyAll();
    },

    applyAll() {
        document.querySelectorAll('[data-i18n]').forEach(el => {
            const key = el.getAttribute('data-i18n');
            const translated = this.t(key);
            if (el.tagName === 'INPUT' && el.hasAttribute('placeholder')) {
                el.placeholder = translated;
            } else {
                el.textContent = translated;
            }
        });
        document.querySelectorAll('[data-i18n-title]').forEach(el => {
            el.title = this.t(el.getAttribute('data-i18n-title'));
        });
    },

    getLangs() {
        return Object.keys(translations).map(code => ({
            code,
            flag: translations[code].flag,
            name: translations[code].name
        }));
    },

    init() {
        document.documentElement.lang = this.lang;
    }
};

I18N.init();
