/*
 * Copyright 2019-2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.moduliths.model;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.moduliths.Modulith;
import org.moduliths.Modulithic;
import org.moduliths.model.Types.SpringTypes;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.util.Assert;

/**
 * Creates a new {@link ModulithMetadata} representing the defaults of {@link Modulithic} but without the annotation
 * present.
 *
 * @author Oliver Drotbohm
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
class DefaultModulithMetadata implements ModulithMetadata {

	private static final Class<? extends Annotation> AT_SPRING_BOOT_APPLICATION = Types
			.loadIfPresent(SpringTypes.AT_SPRING_BOOT_APPLICATION);

	private final @NonNull Object modulithSource;

	/**
	 * Creates a new {@link ModulithMetadata} representing the defaults of a class annotated but not customized with
	 * {@link Modulithic} or {@link Modulith}.
	 *
	 * @param annotated must not be {@literal null}.
	 * @return
	 */
	public static Optional<ModulithMetadata> of(Class<?> annotated) {

		Assert.notNull(annotated, "Annotated type must not be null!");

		return Optional.ofNullable(AT_SPRING_BOOT_APPLICATION) //
				.filter(it -> AnnotatedElementUtils.hasAnnotation(annotated, it)) //
				.map(__ -> new DefaultModulithMetadata(annotated));
	}

	/**
	 * Creates a new {@link ModulithMetadata} from the given package name.
	 *
	 * @param javaPackage must not be {@literal null} or empty.
	 * @return will never be {@literal null}.
	 * @since 1.1
	 */
	public static ModulithMetadata of(String javaPackage) {

		Assert.hasText(javaPackage, "Package name must not be null or empty!");

		return new DefaultModulithMetadata(javaPackage);
	}

	/*
	 * (non-Javadoc)
	 * @see org.moduliths.model.ModulithMetadata#getModulithSource()
	 */
	@Override
	public Object getModulithSource() {
		return modulithSource;
	}

	/*
	 * (non-Javadoc)
	 * @see org.moduliths.model.ModulithMetadata#getAdditionalPackages()
	 */
	@Override
	public List<String> getAdditionalPackages() {
		return Collections.emptyList();
	}

	/*
	 * (non-Javadoc)
	 * @see org.moduliths.model.ModulithMetadata#useFullyQualifiedModuleNames()
	 */
	@Override
	public boolean useFullyQualifiedModuleNames() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see org.moduliths.model.ModulithMetadata#getSharedModuleNames()
	 */
	@Override
	public Stream<String> getSharedModuleNames() {
		return Stream.empty();
	}

	/*
	 * (non-Javadoc)
	 * @see org.moduliths.model.ModulithMetadata#getSystemName()
	 */
	@Override
	public Optional<String> getSystemName() {
		return Optional.empty();
	}
}
