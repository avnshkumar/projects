// config.ts
export interface Config {
    BACKEND_URL: string;
    DEBUG: boolean;
}

const defaultConfig: Config = {
    BACKEND_URL: 'http://localhost:8081',
    DEBUG: false,
};

const config: Record<string, Config> = {
    development: {
        ...defaultConfig,
        DEBUG: true,
    },
    dev: {
        ...defaultConfig,
        BACKEND_URL: 'http://localhost:8081',
    },
    staging: {
        ...defaultConfig,
        BACKEND_URL: 'https://staging.example.com',
    },
    prod: {
        BACKEND_URL: 'https://api.example.com',
        DEBUG: false,
    },
};

export const getConfigForEnvironment = (mode: string): Config => {
    return mode ? config[mode] : config.development;
};