/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.maven.test.sb.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

import com.liferay.maven.test.sb.exception.NoSuchSubmissionException;
import com.liferay.maven.test.sb.model.Submission;
import com.liferay.maven.test.sb.service.SubmissionLocalServiceUtil;
import com.liferay.maven.test.sb.service.persistence.SubmissionPersistence;
import com.liferay.maven.test.sb.service.persistence.SubmissionUtil;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.TransactionalTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.junit.runner.RunWith;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @generated
 */
@RunWith(Arquillian.class)
public class SubmissionPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED));

	@Before
	public void setUp() {
		_persistence = SubmissionUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<Submission> iterator = _submissions.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Submission submission = _persistence.create(pk);

		Assert.assertNotNull(submission);

		Assert.assertEquals(submission.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		Submission newSubmission = addSubmission();

		_persistence.remove(newSubmission);

		Submission existingSubmission = _persistence.fetchByPrimaryKey(newSubmission.getPrimaryKey());

		Assert.assertNull(existingSubmission);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addSubmission();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Submission newSubmission = _persistence.create(pk);

		newSubmission.setGroupId(RandomTestUtil.nextLong());

		newSubmission.setCompanyId(RandomTestUtil.nextLong());

		newSubmission.setUserId(RandomTestUtil.nextLong());

		newSubmission.setUserName(RandomTestUtil.randomString());

		newSubmission.setCreateDate(RandomTestUtil.nextDate());

		newSubmission.setModifiedDate(RandomTestUtil.nextDate());

		newSubmission.setAssignmentId(RandomTestUtil.nextLong());

		newSubmission.setStudentId(RandomTestUtil.nextLong());

		newSubmission.setSubmitDate(RandomTestUtil.nextDate());

		newSubmission.setComment(RandomTestUtil.randomString());

		newSubmission.setGrade(RandomTestUtil.nextInt());

		_submissions.add(_persistence.update(newSubmission));

		Submission existingSubmission = _persistence.findByPrimaryKey(newSubmission.getPrimaryKey());

		Assert.assertEquals(existingSubmission.getSubmissionId(),
			newSubmission.getSubmissionId());
		Assert.assertEquals(existingSubmission.getGroupId(),
			newSubmission.getGroupId());
		Assert.assertEquals(existingSubmission.getCompanyId(),
			newSubmission.getCompanyId());
		Assert.assertEquals(existingSubmission.getUserId(),
			newSubmission.getUserId());
		Assert.assertEquals(existingSubmission.getUserName(),
			newSubmission.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingSubmission.getCreateDate()),
			Time.getShortTimestamp(newSubmission.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingSubmission.getModifiedDate()),
			Time.getShortTimestamp(newSubmission.getModifiedDate()));
		Assert.assertEquals(existingSubmission.getAssignmentId(),
			newSubmission.getAssignmentId());
		Assert.assertEquals(existingSubmission.getStudentId(),
			newSubmission.getStudentId());
		Assert.assertEquals(Time.getShortTimestamp(
				existingSubmission.getSubmitDate()),
			Time.getShortTimestamp(newSubmission.getSubmitDate()));
		Assert.assertEquals(existingSubmission.getComment(),
			newSubmission.getComment());
		Assert.assertEquals(existingSubmission.getGrade(),
			newSubmission.getGrade());
	}

	@Test
	public void testCountByGroupId() throws Exception {
		_persistence.countByGroupId(RandomTestUtil.nextLong());

		_persistence.countByGroupId(0L);
	}

	@Test
	public void testCountByG_A() throws Exception {
		_persistence.countByG_A(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong());

		_persistence.countByG_A(0L, 0L);
	}

	@Test
	public void testCountByStudentId() throws Exception {
		_persistence.countByStudentId(RandomTestUtil.nextLong());

		_persistence.countByStudentId(0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		Submission newSubmission = addSubmission();

		Submission existingSubmission = _persistence.findByPrimaryKey(newSubmission.getPrimaryKey());

		Assert.assertEquals(existingSubmission, newSubmission);
	}

	@Test(expected = NoSuchSubmissionException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<Submission> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("DXP_Submission",
			"submissionId", true, "groupId", true, "companyId", true, "userId",
			true, "userName", true, "createDate", true, "modifiedDate", true,
			"assignmentId", true, "studentId", true, "submitDate", true,
			"comment", true, "grade", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		Submission newSubmission = addSubmission();

		Submission existingSubmission = _persistence.fetchByPrimaryKey(newSubmission.getPrimaryKey());

		Assert.assertEquals(existingSubmission, newSubmission);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Submission missingSubmission = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingSubmission);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		Submission newSubmission1 = addSubmission();
		Submission newSubmission2 = addSubmission();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newSubmission1.getPrimaryKey());
		primaryKeys.add(newSubmission2.getPrimaryKey());

		Map<Serializable, Submission> submissions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, submissions.size());
		Assert.assertEquals(newSubmission1,
			submissions.get(newSubmission1.getPrimaryKey()));
		Assert.assertEquals(newSubmission2,
			submissions.get(newSubmission2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, Submission> submissions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(submissions.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		Submission newSubmission = addSubmission();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newSubmission.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, Submission> submissions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, submissions.size());
		Assert.assertEquals(newSubmission,
			submissions.get(newSubmission.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, Submission> submissions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(submissions.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		Submission newSubmission = addSubmission();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newSubmission.getPrimaryKey());

		Map<Serializable, Submission> submissions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, submissions.size());
		Assert.assertEquals(newSubmission,
			submissions.get(newSubmission.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = SubmissionLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<Submission>() {
				@Override
				public void performAction(Submission submission) {
					Assert.assertNotNull(submission);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		Submission newSubmission = addSubmission();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Submission.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("submissionId",
				newSubmission.getSubmissionId()));

		List<Submission> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Submission existingSubmission = result.get(0);

		Assert.assertEquals(existingSubmission, newSubmission);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Submission.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("submissionId",
				RandomTestUtil.nextLong()));

		List<Submission> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		Submission newSubmission = addSubmission();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Submission.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"submissionId"));

		Object newSubmissionId = newSubmission.getSubmissionId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("submissionId",
				new Object[] { newSubmissionId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingSubmissionId = result.get(0);

		Assert.assertEquals(existingSubmissionId, newSubmissionId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Submission.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"submissionId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("submissionId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected Submission addSubmission() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Submission submission = _persistence.create(pk);

		submission.setGroupId(RandomTestUtil.nextLong());

		submission.setCompanyId(RandomTestUtil.nextLong());

		submission.setUserId(RandomTestUtil.nextLong());

		submission.setUserName(RandomTestUtil.randomString());

		submission.setCreateDate(RandomTestUtil.nextDate());

		submission.setModifiedDate(RandomTestUtil.nextDate());

		submission.setAssignmentId(RandomTestUtil.nextLong());

		submission.setStudentId(RandomTestUtil.nextLong());

		submission.setSubmitDate(RandomTestUtil.nextDate());

		submission.setComment(RandomTestUtil.randomString());

		submission.setGrade(RandomTestUtil.nextInt());

		_submissions.add(_persistence.update(submission));

		return submission;
	}

	private List<Submission> _submissions = new ArrayList<Submission>();
	private SubmissionPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}