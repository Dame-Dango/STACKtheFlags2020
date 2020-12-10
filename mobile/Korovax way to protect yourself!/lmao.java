import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class lmao {
  public static void main(String[] args) {
    try {
      String key = "I can do this all day.";
      String salt = "Hulk smash!";
      String enc = "fFFFx2ezHvklL5t3ViKP2qQtj4oGwL1zL7Ln5rKNafM=";
      c a = c.q(key, salt, new byte[16]);
      a.m();
      byte dataBytes[] = f.a(enc, a.o());
      SecretKey secretKey = getSecretKey(hashTheKey(a.v(), a), a);
      Cipher cipher = Cipher.getInstance(a.n());
      cipher.init(2, secretKey, a.u(), a.A());
      System.out.println(new String(cipher.doFinal(dataBytes)));
    } catch (Exception ex) {
      System.out.println(ex.getMessage());
    }
  }

  private static SecretKey getSecretKey(char[] key, c m) {
    try {
      return new SecretKeySpec(SecretKeyFactory.getInstance(m.z()).generateSecret(new PBEKeySpec(key, m.y().getBytes(m.p()), m.s(), m.x())).getEncoded(), m.w());
    } catch (Exception ex) {
      System.out.println(ex.getMessage());
      return null;
    }
  }

  private static char[] hashTheKey(String key, c m) {
    try {
      MessageDigest messageDigest = MessageDigest.getInstance(m.r());
      messageDigest.update(key.getBytes(m.p()));
      return f.f(messageDigest.digest(), 1).toCharArray();
    } catch (Exception ex) {
    }
    return null;
  }

  public static class c {

    /* renamed from: a  reason: collision with root package name */
    public byte[] f2916a;

    /* renamed from: b  reason: collision with root package name */
    public int f2917b;

    /* renamed from: c  reason: collision with root package name */
    public int f2918c;

    /* renamed from: d  reason: collision with root package name */
    public int f2919d;

    /* renamed from: e  reason: collision with root package name */
    public String f2920e;

    /* renamed from: f  reason: collision with root package name */
    public String f2921f;

    /* renamed from: g  reason: collision with root package name */
    public String f2922g;
    public String h;
    public String i;
    public String j;
    public String k;
    public String l;
    public SecureRandom m;
    public IvParameterSpec n;

    public static c q(String key, String salt, byte[] iv) {
        c cVar = new c();
        cVar.H(iv);
        cVar.J(key);
        cVar.M(salt);
        cVar.L(128);
        cVar.K("AES");
        cVar.E("UTF8");
        cVar.G(1);
        cVar.F("SHA1");
        cVar.D(0);
        cVar.C("AES/CBC/PKCS5Padding");
        cVar.P("SHA1PRNG");
        cVar.N("PBKDF2WithHmacSHA1");
        return cVar;
    }

    public void m() {
      try {
        O(SecureRandom.getInstance(B()));
        I(new IvParameterSpec(t()));
      } catch (Exception ex) {}
    }

    public final String p() {
        return this.i;
    }

    public c E(String charsetName) {
        this.i = charsetName;
        return this;
    }

    public final String n() {
        return this.f2922g;
    }

    public c C(String algorithm) {
        this.f2922g = algorithm;
        return this;
    }

    public final String w() {
        return this.h;
    }

    public c K(String keyAlgorithm) {
        this.h = keyAlgorithm;
        return this;
    }

    public final int o() {
        return this.f2918c;
    }

    public c D(int base64Mode) {
        this.f2918c = base64Mode;
        return this;
    }

    public final String z() {
        return this.j;
    }

    public c N(String secretKeyType) {
        this.j = secretKeyType;
        return this;
    }

    public final String y() {
        return this.f2920e;
    }

    public c M(String salt) {
        this.f2920e = salt;
        return this;
    }

    public final String v() {
        return this.f2921f;
    }

    public c J(String key) {
        this.f2921f = key;
        return this;
    }

    public final int x() {
        return this.f2917b;
    }

    public c L(int keyLength) {
        this.f2917b = keyLength;
        return this;
    }

    public final int s() {
        return this.f2919d;
    }

    public c G(int iterationCount) {
        this.f2919d = iterationCount;
        return this;
    }

    public final String B() {
        return this.l;
    }

    public c P(String secureRandomAlgorithm) {
        this.l = secureRandomAlgorithm;
        return this;
    }

    public final byte[] t() {
        return this.f2916a;
    }

    public c H(byte[] iv) {
        this.f2916a = iv;
        return this;
    }

    public final SecureRandom A() {
        return this.m;
    }

    public c O(SecureRandom secureRandom) {
        this.m = secureRandom;
        return this;
    }

    public final IvParameterSpec u() {
        return this.n;
    }

    public c I(IvParameterSpec ivParameterSpec) {
        this.n = ivParameterSpec;
        return this;
    }

    public final String r() {
        return this.k;
    }

    public c F(String digestAlgorithm) {
        this.k = digestAlgorithm;
        return this;
    }
}
}