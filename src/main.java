import java.security.SecureRandom;

import uk.ac.ic.doc.jpair.api.FieldElement;
import uk.ac.ic.doc.jpair.api.Pairing;
import uk.ac.ic.doc.jpair.pairing.BigInt;
import uk.ac.ic.doc.jpair.pairing.PairingFactory;
import uk.ac.ic.doc.jpair.pairing.Point;
import org.apache.commons.codec.binary.Hex;
public class main {

	public static void main(String[] args) {
		
		
		Pairing e = PairingFactory.ssTate(160, 256, new SecureRandom());
		Point P=e.RandomPointInG1(new SecureRandom());//randomly choose a point P
		
		BigInt A = new BigInt("2");
		BigInt B = new BigInt("3");
		BigInt A_B = A.add(B);
		
		System.out.println("A = " + A);
		System.out.println("B = " + B);
		
		Point AP = e.getCurve().multiply(P, A);
		Point BP = e.getCurve().multiply(P, B);
		
		System.out.println("P:" + P);
		System.out.println("A x P:" + AP);
		System.out.println("B x P:" + BP);
		
		Point BAP = e.getCurve().multiply(AP, B);
		Point ABP = e.getCurve().multiply(BP, A);
		
		System.out.println("B x A x P:" + BAP);
		System.out.println("A x B x P:" + ABP);
		
		
		System.out.println("e((A+B)P, P) =? e(AP, P)e(BP, P)");
		
		Point A_BP = e.getCurve().multiply(P, A_B);
		
		FieldElement Result_A = e.compute(A_BP, P);
		
		FieldElement Result_B_1 = e.compute(AP, P);
		FieldElement Result_B_2 = e.compute(BP, P);
		
		FieldElement Result_B = e.getGt().multiply(Result_B_1, Result_B_2);
		
		System.out.println("e((A+B)P, P)    : " + new String(Hex.encodeHex(Result_A.toByteArray())));
		System.out.println("e( A   P, P)    : " + new String(Hex.encodeHex(Result_B_1.toByteArray())));
		System.out.println("e(   B P, P)    : " + new String(Hex.encodeHex(Result_B_2.toByteArray())));
		
		System.out.println("e(AP, P)e(BP, P): " + new String(Hex.encodeHex(Result_B.toByteArray())));
		
		System.out.println("e((A+B)P, P) == e(AP, P)e(BP, P): " + Result_A.equals(Result_B));
		
	}

}
