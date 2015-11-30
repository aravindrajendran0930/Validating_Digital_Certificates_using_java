import java.security.Key;
import java.security.KeyStore;
import java.io.FileInputStream;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DigitalCert {
	static int i = 0;

	public static void main(String args[]) throws Exception {
		// Reading the necessary files
		FileInputStream taFileReader = new FileInputStream("Trustcenter.cer");
		FileInputStream userPubKeyReader = new FileInputStream("Raghupub.cer");
		FileInputStream userPriKeyReader = new FileInputStream("Raghupri.pfx");

		// Public key of the Trust Authority
		CertificateFactory certFact1 = CertificateFactory.getInstance("X509");
		X509Certificate taCertOutput = (X509Certificate) certFact1
				.generateCertificate(taFileReader);
		System.out
				.println("\n Printing the certificate of Certificate Authority");
		System.out.println("\nCertificate for: " + taCertOutput.getSubjectDN());
		System.out.println("\n The Certificate Authority's cert content is : "
				+ taCertOutput.getPublicKey());
		System.out.println("\tCertificate Number "
				+ taCertOutput.getSerialNumber());
		System.out.println("\tValidity :  " + taCertOutput.getNotBefore()
				+ " till " + taCertOutput.getNotAfter());
		System.out.println("\tCertificate issued by : "
				+ taCertOutput.getIssuerDN());

		// Printing the Public key of TA

		for (i = 0; i < 150; i++) {
			System.out.print("*");
		}
		System.out.println("\n The Certificate Authority's Public Key is : "
				+ taCertOutput.getPublicKey());

		// User's Certificate contents
		for (i = 0; i < 150; i++) {
			System.out.print("*");
		}
		System.out.println("");
		CertificateFactory certFact2 = CertificateFactory.getInstance("X509");
		X509Certificate userCertOutput = (X509Certificate) certFact2
				.generateCertificate(userPubKeyReader);
		System.out.println("\nCertificate for: " + taCertOutput.getSubjectDN());
		System.out.println("\n The user's cert content is : "
				+ userCertOutput.getPublicKey());
		System.out.println("\tCertificate Number "
				+ userCertOutput.getSerialNumber());
		System.out.println("\tValidity :  " + userCertOutput.getNotBefore()
				+ " till " + userCertOutput.getNotAfter());
		Date validDate = userCertOutput.getNotAfter();
		System.out.println("\tCertificate issued by : "
				+ userCertOutput.getIssuerDN());
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		System.out.println("current date" + dateFormat.format(cal.getTime()));
		Date today = dateFormat.parse(dateFormat.format(cal.getTime()));
		int n = today.compareTo(validDate);
		// to check for the certificate's validity
		for (i = 0; i < 150; i++) {
			System.out.print("*");
		}
		System.out.println("Date on cert is : " + validDate);
		System.out.println("Date today is : " + today);
		if (n > 0) {
			System.out.println("Certificate is invalid !!!");
		} else {
			System.out.println("Certificate is valid !!!");
		}
		for (i = 0; i < 150; i++) {
			System.out.print("*");
		}
		// Calculating the user's private key
		KeyStore ks = KeyStore.getInstance("pkcs12", "SunJSSE");
		ks.load(userPriKeyReader, "raghu".toCharArray());
		String keyAlias = ks.aliases().nextElement();
		Key userPrivKey = ks.getKey(keyAlias, "raghu".toCharArray()); 
		System.out.println("\n");
		System.out.println("\nThe Private key of user is : " + userPrivKey);
		for (i = 0; i < 150; i++) {
			System.out.print("*");
		}
		System.out.println("\nThe Public key of user is : "
				+ userCertOutput.getSubjectX500Principal());
		System.out.println(userCertOutput.getPublicKey());
		for (i = 0; i < 150; i++) {
			System.out.print("*");
		}

	}
}