export interface PaymentEntity {
    id: string
    description: string
    status: string
    paymentValueInCents: number
    payedAt: number[] | null
}