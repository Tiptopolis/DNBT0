package Metatron.Core;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

import Metatron.Core.Math.N_Operator;
import Metatron.Core.Utils.StringUtils;
import squidpony.squidmath.RNG;

public class M_Utils {

	
	
	
	
	public static RNG RND = new RNG();
	public static float DeltaTime = 0f;
	public static int Width = 0;
	public static int Height = 0;
	

	
    private static String readFile(String sourceFile) {
        try (Scanner input = new Scanner(new File(sourceFile))) {
            StringBuilder sb = new StringBuilder();
            while (input.hasNext()) { // read all lines and append to String Builder
                sb.append(input.nextLine());
            }
            return sb.toString();
        } catch (Exception ex) {
            // if the file does not exist the exception will be caught here
            System.out.println(ex.getMessage());
            return null;
        }
    }
	
	
	public static void Log() {
		System.out.println("\n");
	}

	public static void Log(Object... out) {
		for (Object o : out)
			System.out.println("" + o);
	}

	/*
	 * public static void Log(iFunctor.Supplier s) { if (s != null && s.get() !=
	 * null) Log(s.get()); else Log("null"); }
	 */

	public static void Log(Supplier s) {
		if (s != null && s.get() != null)
			Log(s.get());
		else
			Log("null");
	}

	public static String[] charArray(String s) {
		String[] res = new String[s.length()];

		for (int i = 0; i < s.length(); i++)
			res[i] = "" + s.charAt(i);

		return res;
	}

	//
	////
	// @Annotation Reflection hax
	//
	// Instead of AccessibleObject.getAnnotation(Class annotationClass) use
	private static Annotation getAnnotation(AccessibleObject object, Class annotationClass) {
		for (Annotation a : object.getAnnotations()) {
			if (a.annotationType().getCanonicalName().equals(annotationClass.getCanonicalName()))
				return a;
		}
		return null;
	}

	// Instead of MyAnnotation.value() use
	private static Object getAnnotationFieldWithReflection(Annotation annotation, String fieldName)
			throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		return annotation.annotationType().getMethod(fieldName).invoke(annotation);
	}

	public static <T> boolean isInstanceOf(Class<T> clazz, Class<T> targetClass) {
		return clazz.isInstance(targetClass);
	}

	public boolean isArray(Object o) {
		return o.getClass().isArray();
	}

	// can be used as a basic object parser
	@SuppressWarnings("unused")
	static public void printArray(Object what) {
		System.out.println("PRINT ARRAY: " + what + " " + what.getClass());
		System.out.println("           : " + what.getClass().getName());
		System.out.println("           : " + what.getClass().getSimpleName());
		System.out.println("           : " + what.getClass().getCanonicalName());
		System.out.println("");

		if (what == null) {
			// special case since this does fuggly things on > 1.1
			System.out.println("null");

		} else {
			String name = what.getClass().getName();
			if (name.charAt(0) == '[') {
				switch (name.charAt(1)) {
				case '[':
					// don't even mess with multi-dimensional arrays (case '[')
					// or anything else that's not int, float, boolean, char
					System.out.println(what);
					break;

				case 'L':
					// print a 1D array of objects as individual elements
					Object poo[] = (Object[]) what;
					for (int i = 0; i < poo.length; i++) {
						if (poo[i] instanceof String) {
							System.out.println("[" + i + "] \"" + poo[i] + "\"" + " -|String|");
						} else {
							System.out.println("[" + i + "] " + poo[i]);
						}
					}
					break;

				case 'Z': // boolean
					boolean zz[] = (boolean[]) what;
					for (int i = 0; i < zz.length; i++) {
						System.out.println("[" + i + "] " + zz[i] + " -|boolean|");
					}
					break;

				case 'B': // byte
					byte bb[] = (byte[]) what;
					for (int i = 0; i < bb.length; i++) {
						System.out.println("[" + i + "] " + bb[i] + " -|byte|");
					}
					break;

				case 'C': // char
					char cc[] = (char[]) what;
					for (int i = 0; i < cc.length; i++) {
						System.out.println("[" + i + "] '" + cc[i] + "'" + " -|char|");
					}
					break;

				case 'I': // int
					int ii[] = (int[]) what;
					for (int i = 0; i < ii.length; i++) {
						System.out.println("[" + i + "] " + ii[i] + " -|int|");
					}
					break;

				case 'J': // lonn int
					long jj[] = (long[]) what;
					for (int i = 0; i < jj.length; i++) {
						System.out.println("[" + i + "] " + jj[i] + " -|long|");
					}
					break;

				case 'F': // float
					float ff[] = (float[]) what;
					for (int i = 0; i < ff.length; i++) {
						System.out.println("[" + i + "] " + ff[i] + " -|float|");
					}
					break;

				case 'D': // double
					double dd[] = (double[]) what;
					for (int i = 0; i < dd.length; i++) {
						System.out.println("[" + i + "] " + dd[i] + " -|double|");
					}
					break;

				default:
					System.out.println(what);
				}
			} else { // not an array
				System.out.println(what.toString());
			}
		}
		System.out.flush();
	}

	private static void __RANDOM_NUMBERS__() {
	};

	public static Number rnd(Number min, Number max) {
		if (max instanceof Integer)
			return rndInt(max.intValue(), max.intValue());

		else
			return rndFlt(max.floatValue(), max.floatValue());
	}

	public static int rndInt(int max) {
		return rndInt(0, max);
	}

	public static int rndInt(int min, int max) {
		return RND.between(min, max);
	}

	public static float rndFlt(float max) {
		return (float) RND.between(0f, max);
	}

	public static float rndFlt(float min, float max) {
		return (float) RND.between(min, max);
	}

	public static Object getRandom(Object... things) {
		RNG R = new RNG();
		int at = R.nextInt(things.length - 1);
		// int at = RNG.rndInt(0, things.length - 1);
		Log(" :: " + at);
		return things[at];
	}

	public static Object[] sample(Object[] things) {
		int size = things.length;

		ArrayList<Object> list = new ArrayList<Object>();
		int c = 0;
		while (c < size - 1) {
			Object next = getRandom(things);
			Log(list.size() + " <" + next + " " + next.getClass().getSimpleName());
			if (!list.contains(next)) {
				list.add(next);
				c++;
			}
			Log(list.get(0).equals(next));
		}

		return list.toArray();
	}

	public static ArrayList<Object> sampleCollection(Object[] things, int size) {
		if (size > things.length)
			size = things.length;
		ArrayList<Object> list = new ArrayList<Object>();
		int c = 0;
		while (c < size - 1) {
			Object next = getRandom(things);
			Log(list.size() + " <" + next + " " + next.getClass().getSimpleName());
			if (!list.contains(next)) {
				list.add(next);
				c++;
			}
			Log(list.get(0).equals(next));
		}

		return list;
	}

	
	public static String toHashId(Object obj) {
		return Integer.toHexString(obj.hashCode());
	}

	public static String toIdString(Object obj) {

		if (obj instanceof String || obj instanceof Number)
			return ("" + obj);
		else
			return obj.getClass().getSimpleName() + "@" + Integer.toHexString(obj.hashCode());
	}

	public static String toToken(Class obj) {
		String tag = "";
		tag = obj.getSimpleName();
		return "<" + tag + ">";
	}

	public static String toToken(Object obj) {
		String tag = "";
		tag = obj.getClass().getSimpleName();
		return "<" + tag + ">";
	}

	public static <T> Predicate<T> combineFilters(Predicate<T>... predicates) {

		Predicate<T> p = Stream.of(predicates).reduce(x -> true, Predicate::and);
		return p;

	}

	public static Predicate instanceOf(Class c) {
		return o -> c.isAssignableFrom(o.getClass());
	}

	public static Predicate instanceOf(Class... C) {
		int l = C.length;
		Predicate[] P = new Predicate[l];
		P[0] = instanceOf(C[0]);
		for (int i = 0; i < l; i++) {
			if (i != 0)
				P[i] = instanceOf(C[i]);
		}
		return any(P);
	}

	public static Predicate instanceOf(Object c) {
		return o -> o.getClass().isAssignableFrom(c.getClass());
	}

	public static Predicate instanceOf(Object... C) {
		int l = C.length;
		Predicate[] P = new Predicate[l];
		P[0] = instanceOf(C[0].getClass());
		for (int i = 0; i < l; i++) {
			if (i != 0)
				P[i] = instanceOf(C[i].getClass());
		}
		return any(P);
	}

	public static Predicate kindOf(Class c) {
		return o -> StringUtils.isFormOf(o.getClass().getSimpleName(), c.getSimpleName());
	}

	public static Predicate kindOf(Object c) {
		return kindOf(c.getClass());
	}

	public static Predicate kindOf(Class... C) {
		int l = C.length;
		Predicate[] P = new Predicate[l];
		P[0] = kindOf(C[0]);
		for (int i = 0; i < l; i++) {
			if (i != 0)
				P[i] = kindOf(C[i]);
		}
		return any(P);
	}

	public static Predicate kindOf(Object... C) {
		int l = C.length;
		Predicate[] P = new Predicate[l];
		P[0] = kindOf(C[0].getClass());
		for (int i = 0; i < l; i++) {
			if (i != 0)
				P[i] = kindOf(C[i].getClass());
		}
		return any(P);
	}

	public static boolean AllMatch(Predicate P, Object... O) {
		for (Object o : O)
			if (!P.test(o))
				return false;

		return true;
	}

	public static <T> Predicate<T> isValue(T v) {
		if (v instanceof Number)
			return o -> N_Operator.isEqual((Number) o, (Number) v);
		else
			return o -> o.equals(v);
	}

	public static <T> Predicate<T> isAnyValue(T... v) {
		int l = v.length;
		Predicate[] P = new Predicate[l];
		P[0] = isValue(v[0]);
		for (int i = 0; i < l; i++) {
			if (i != 0)
				P[i] = isValue(v[i]);
		}
		return any(P);
	}

	public static <T> Predicate<T> isReference(T v) {
		return o -> o == v;
	}

	public static <T> Predicate<T> isAnyReference(T... v) {
		int l = v.length;
		Predicate[] P = new Predicate[l];
		P[0] = isReference(v[0]);
		for (int i = 0; i < l; i++) {
			if (i != 0)
				P[i] = isReference(v[i]);
		}
		return any(P);
	}

	public static Predicate any(Predicate... p) {
		Predicate init = p[0];
		if (p.length > 1) {
			Predicate out = init;
			for (Predicate P : p)
				out = out.or(P);
			return out;
		} else
			return init;
	}

	public static Predicate all(Predicate... p) {
		Predicate init = p[0];
		if (p.length > 1) {
			Predicate out = init;
			for (Predicate P : p)
				out = out.and(P);
			return out;
		} else
			return init;
	}

	
	public static boolean equalsAny(String a, String...c)
	{
		return StringUtils.equals(a, c);
	}
	
	public static BiFunction equals() {
		return (a, b) -> {
			return a.equals(b);
		};
	}

	public static Function<Comparable, Integer> comparison(Comparable a) {
		return (o) -> (a.compareTo(o));
	}

	public static Comparator compareUsing(Function f) {
		return Comparator.comparing(f);
	}

	public static Comparator equality() {
		return compareUsing((Function) equals());
	}

	public static Predicate isEqual(Object other) {
		return (o) -> (o == other || o.equals(other));
	}
}
