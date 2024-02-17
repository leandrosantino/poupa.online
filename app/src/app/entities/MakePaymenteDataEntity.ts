export interface MakePaymenteDataEntity {
    id: number
    qrCode: string
    qrCodeBase64: string
    status: 'PROCESSING' | 'PENDING' | 'COMPLETED'
    currencyId: string
    description: string
    dateOfExpiration: number[]
}