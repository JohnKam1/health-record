// appoption.ts
export interface IAppOption {
    globalData: {
        token: string
        baseUrl: string
    }
    onLaunch?: () => void
}