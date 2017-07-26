package com.pingcap.spark

import java.util.Properties

import org.apache.spark.sql.SparkSession

/**
  * Created by ilovesoup1 on 26/07/2017.
  */
class Tpch10(spark: SparkSession, prop: Properties) extends Tpch(spark, prop) {

  override def sparkQuery =
    """select
      |	c_custkey,
      |	c_name,
      |	sum(l_extendedprice * (1 - l_discount)) as revenue,
      |	c_acctbal,
      |	n_name,
      |	c_address,
      |	c_phone,
      |	c_comment
      |from
      |	customer,
      |	orders,
      |	lineitem,
      |	nation
      |where
      |	c_custkey = o_custkey
      |	and l_orderkey = o_orderkey
      |	and o_orderdate >= date '1993-10-01'
      |	and o_orderdate < date '1993-10-01' + interval '3' month
      |	and l_returnflag = 'R'
      |	and c_nationkey = n_nationkey
      |group by
      |	c_custkey,
      |	c_name,
      |	c_acctbal,
      |	c_phone,
      |	n_name,
      |	c_address,
      |	c_comment
      |order by
      |	revenue desc, c_custkey
    """.stripMargin

  override def tidbQuery =
    """
      select
 |        	c_custkey,
 |        	c_name,
 |        	sum(l_extendedprice * (1 - l_discount)) as revenue,
 |        	c_acctbal,
 |        	n_name,
 |        	c_address,
 |        	c_phone,
 |        	c_comment
 |        from
 |        	customer,
 |        	orders,
 |        	lineitem,
 |        	nation
 |        where
 |        	c_custkey = o_custkey
 |        	and l_orderkey = o_orderkey
 |        	and o_orderdate >= '1993-10-01'
 |        	and o_orderdate < '1994-01-01'
 |        	and l_returnflag = 'R'
 |        	and c_nationkey = n_nationkey
 |        group by
 |        	c_custkey,
 |        	c_name,
 |        	c_acctbal,
 |        	c_phone,
 |        	n_name,
 |        	c_address,
 |        	c_comment
 |        order by
 |        	revenue desc, c_custkey;
    """.stripMargin
}
