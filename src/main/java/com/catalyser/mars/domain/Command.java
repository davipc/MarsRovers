package com.catalyser.mars.domain;

import java.util.List;
import java.util.stream.Collectors;

public enum Command {
	L("Left"), R("Right"), M("Move");
	
	private String description;

	public static List<Command> parseCommands(String strCommands) {
		return strCommands.chars().mapToObj(c -> Command.valueOf(""+(char)c)).collect(Collectors.toList());
	}
	private Command(String desc) {
		this.description = desc;
	}
	
	public String getDescription() {
		return description;
	}
}
