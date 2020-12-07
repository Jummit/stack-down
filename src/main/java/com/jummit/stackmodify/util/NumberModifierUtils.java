package com.jummit.stackmodify.util;

public class NumberModifierUtils {
	
	public NumberModifier modifiers[] = new NumberModifier[] {
		new AddNumberModifier(),
		new SubtractNumberModifier(),
		new AssignNumberModifier(),
		new MultiplyNumberModifier(),
		new DivideNumberModifier(),
	};

	public abstract class NumberModifier {

		private final String modifier;

		public NumberModifier(String modifier) {
			this.modifier = modifier;
		}

		public abstract Integer modify(Integer base, Integer number);

		public String getModifier() {
			return modifier;
		}
		
	}

	public class AddNumberModifier extends NumberModifier {
		public AddNumberModifier() {
			super("+");
		}

		@Override
		public Integer modify(Integer base, Integer number) {
			return base + number;
		}
	}

	public class SubtractNumberModifier extends NumberModifier {
		public SubtractNumberModifier() {
			super("-");
		}

		@Override
		public Integer modify(Integer base, Integer number) {
			return base - number;
		}
	}

	public class AssignNumberModifier extends NumberModifier {
		public AssignNumberModifier() {
			super("=");
		}

		@Override
		public Integer modify(Integer base, Integer number) {
			return number;
		}
	}

	public class MultiplyNumberModifier extends NumberModifier {
		public MultiplyNumberModifier() {
			super("*");
		}

		@Override
		public Integer modify(Integer base, Integer number) {
			return base * number;
		}
	}

	public class DivideNumberModifier extends NumberModifier {
		public DivideNumberModifier() {
			super("/");
		}

		@Override
		public Integer modify(Integer base, Integer number) {
			return base / number;
		}
	}

}
