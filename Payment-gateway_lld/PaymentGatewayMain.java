public class PaymentGatewayMain {
    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("          PAYMENT GATEWAY SYSTEM DESIGN           ");
        System.out.println("==================================================");

        PaymentGateway gateway = new PaymentGateway(3);

        // 1. Process Card Payment
        System.out.println("\n--- Step 1: Processing Card Payment ---");
        PaymentRequest cardReq = new PaymentRequest("ORD_1001", 1500.0, PaymentType.CARD, "IDEM_KEY_CARD_1001");
        PaymentResponse cardResp = gateway.processPayment(cardReq);
        System.out.println("Response -> " + cardResp);

        // 2. Process UPI Payment
        System.out.println("\n--- Step 2: Processing UPI Payment ---");
        PaymentRequest upiReq = new PaymentRequest("ORD_1002", 450.0, PaymentType.UPI, "IDEM_KEY_UPI_1002");
        PaymentResponse upiResp = gateway.processPayment(upiReq);
        System.out.println("Response -> " + upiResp);

        // 3. Test IDEMPOTENCY: Duplicate UPI Request with SAME idempotency key
        System.out.println("\n--- Step 3: Duplicate Request (Testing Idempotency) ---");
        System.out.println("Client re-sends request with same Idempotency Key ('IDEM_KEY_UPI_1002') due to network timeout...");
        PaymentRequest duplicateUpiReq = new PaymentRequest("ORD_1002", 450.0, PaymentType.UPI, "IDEM_KEY_UPI_1002");
        PaymentResponse duplicateResp = gateway.processPayment(duplicateUpiReq);
        System.out.println("Response -> " + duplicateResp);
        System.out.println("Same Transaction ID? " + upiResp.getTransactionId().equals(duplicateResp.getTransactionId()));

        // 4. Process NetBanking Payment
        System.out.println("\n--- Step 4: Processing NetBanking Payment ---");
        PaymentRequest nbReq = new PaymentRequest("ORD_1003", 8200.0, PaymentType.NETBANKING, "IDEM_KEY_NB_1003");
        PaymentResponse nbResp = gateway.processPayment(nbReq);
        System.out.println("Response -> " + nbResp);

        // 5. Display Transaction History
        System.out.println("\n==================================================");
        System.out.println("              TRANSACTION AUDIT LOG               ");
        System.out.println("==================================================");
        for (Transaction txn : gateway.getTransactionHistory()) {
            System.out.println("  * " + txn);
        }
        System.out.println("Total Recorded Transactions in DB: " + gateway.getTransactionHistory().size());
        System.out.println("(Notice: Duplicate request did NOT create an extra transaction!)");
    }
}
