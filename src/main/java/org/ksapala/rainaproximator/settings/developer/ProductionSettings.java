package org.ksapala.rainaproximator.settings.developer;

import org.ksapala.rainaproximator.settings.Settings.InjectableSettings;

import java.util.Date;

public class ProductionSettings implements InjectableSettings {

	public ProductionSettings() {
	}

	@Override
	public Date getNow() {
		return new Date();
	}

}
