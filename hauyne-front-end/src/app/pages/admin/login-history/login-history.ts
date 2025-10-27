export interface LoginHistory {
    id: number;
    type: string;
    result: string;
    failReason: string;
    username: string;
    ipAddress: string;
    location: string;
    browser: string;
    browserVersion: string;
    osName: string;
    createdTime: string;
}
