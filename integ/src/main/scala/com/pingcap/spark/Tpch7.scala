package com.pingcap.spark

import java.util.Properties

import org.apache.spark.sql.SparkSession

/**
  * Created by ilovesoup1 on 26/07/2017.
  */
class Tpch7(spark: SparkSession, prop: Properties) extends Tpch(spark, prop) {
  override def testName() = "Tpch7"

  override def sparkQuery =
    """select
      | 	supp_nation,
      | 	cust_nation,
      | 	l_year,
      | 	sum(volume) as revenue
      | from
      | 	(
      | 		select
      | 			n1.n_name as supp_nation,
      | 			n2.n_name as cust_nation,
      | 			year(l_shipdate) as l_year,
      | 			l_extendedprice * (1 - l_discount) as volume
      | 		from
      | 			supplier,
      | 			lineitem,
      | 			orders,
      | 			customer,
      | 			nation n1,
      | 			nation n2
      | 		where
      | 			s_suppkey = l_suppkey
      | 			and o_orderkey = l_orderkey
      | 			and c_custkey = o_custkey
      | 			and s_nationkey = n1.n_nationkey
      | 			and c_nationkey = n2.n_nationkey
      | 			and (
      | 				(n1.n_name = 'JAPAN' and n2.n_name = 'INDIA')
      | 				or (n1.n_name = 'INDIA' and n2.n_name = 'JAPAN')
      | 			)
      | 			and l_shipdate between date '1995-01-01' and date '1996-12-31'
      | 	) as shipping
      | group by
      | 	supp_nation,
      | 	cust_nation,
      | 	l_year
      | order by
      | 	supp_nation,
      | 	cust_nation,
      | 	l_year
    """.stripMargin

  override def tidbQuery =
    """
      |select
      |	supp_nation,
      |	cust_nation,
      |	l_year,
      |	sum(volume) as revenue
      |from
      |	(
      |		select
      |			n1.n_name as supp_nation,
      |			n2.n_name as cust_nation,
      |			extract(year from l_shipdate) as l_year,
      |			l_extendedprice * (1 - l_discount) as volume
      |		from
      |			supplier,
      |			lineitem,
      |			orders,
      |			customer,
      |			nation n1,
      |			nation n2
      |		where
      |			s_suppkey = l_suppkey
      |			and o_orderkey = l_orderkey
      |			and c_custkey = o_custkey
      |			and s_nationkey = n1.n_nationkey
      |			and c_nationkey = n2.n_nationkey
      |			and (
      |				(n1.n_name = 'JAPAN' and n2.n_name = 'INDIA')
      |				or (n1.n_name = 'INDIA' and n2.n_name = 'JAPAN')
      |			)
      |			and l_shipdate between '1995-01-01' and '1996-12-31'
      |	) as shipping
      |group by
      |	supp_nation,
      |	cust_nation,
      |	l_year
      |order by
      |	supp_nation,
      |	cust_nation,
      |	l_year;
    """.stripMargin
}