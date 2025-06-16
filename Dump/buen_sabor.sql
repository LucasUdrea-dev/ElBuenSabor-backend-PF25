CREATE DATABASE  IF NOT EXISTS `buen_sabor` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `buen_sabor`;
-- MySQL dump 10.13  Distrib 8.0.38, for Win64 (x86_64)
--
-- Host: localhost    Database: buen_sabor
-- ------------------------------------------------------
-- Server version	8.0.39

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `articulo`
--

DROP TABLE IF EXISTS `articulo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `articulo` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(255) DEFAULT NULL,
  `es_para_elaborar` bit(1) DEFAULT NULL,
  `existe` bit(1) DEFAULT NULL,
  `imagen_articulo` varchar(255) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `precio` double DEFAULT NULL,
  `subcategoria_id` bigint DEFAULT NULL,
  `unidad_medida_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKqx0exg6w1pcvj75x54snk0nj4` (`subcategoria_id`),
  KEY `FKlf2hbqm1r4qx36lkr0b4mix6b` (`unidad_medida_id`),
  CONSTRAINT `FKlf2hbqm1r4qx36lkr0b4mix6b` FOREIGN KEY (`unidad_medida_id`) REFERENCES `unidad_medida` (`id`),
  CONSTRAINT `FKqx0exg6w1pcvj75x54snk0nj4` FOREIGN KEY (`subcategoria_id`) REFERENCES `subcategoria` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `articulo`
--

LOCK TABLES `articulo` WRITE;
/*!40000 ALTER TABLE `articulo` DISABLE KEYS */;
INSERT INTO `articulo` VALUES (1,'Hamburguesa con carne, lechuga, tomate y queso',_binary '\0',_binary '','hamburguesa.jpg','Hamburguesa Clásica',850,1,1),(2,'Pan especial para hamburguesas',_binary '',_binary '',NULL,'Pan de Hamburguesa',50,3,1),(3,'Medallón de carne vacuna 150g',_binary '',_binary '',NULL,'Medallón de Carne',200,3,1),(4,'Hojas de lechuga fresca',_binary '',_binary '',NULL,'Lechuga',20,3,2),(5,'Feta de queso cheddar',_binary '',_binary '',NULL,'Queso Cheddar',80,3,1),(6,'Bebida gaseosa Coca-Cola 500ml',_binary '\0',_binary '','coca-cola.jpg','Coca-Cola 500ml',150,2,1),(19,'Gigante y deliciosa',_binary '\0',_binary '','ravioles.jpg','Ravioles',9000,1,1),(20,'Tacos a la parrilla',_binary '\0',_binary '','tacos-mexicanos.jpg','Taco',500,1,1),(22,'Pizza muzzarela',_binary '\0',_binary '','pizza.jpg','Pizza muzzarela',9000,1,1);
/*!40000 ALTER TABLE `articulo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `articulo_insumo`
--

DROP TABLE IF EXISTS `articulo_insumo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `articulo_insumo` (
  `precio_compra` double DEFAULT NULL,
  `id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK5yoloai8ewly5lkbi3wl5904y` FOREIGN KEY (`id`) REFERENCES `articulo` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `articulo_insumo`
--

LOCK TABLES `articulo_insumo` WRITE;
/*!40000 ALTER TABLE `articulo_insumo` DISABLE KEYS */;
INSERT INTO `articulo_insumo` VALUES (30,2),(120,3),(10,4),(50,5),(50,6);
/*!40000 ALTER TABLE `articulo_insumo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `articulo_manufacturado`
--

DROP TABLE IF EXISTS `articulo_manufacturado`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `articulo_manufacturado` (
  `preparacion` varchar(255) DEFAULT NULL,
  `tiempo_estimado` varchar(255) DEFAULT NULL,
  `id` bigint NOT NULL,
  `sucursal_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKq0sulc6jjw51efw0g1yf9cvs2` (`sucursal_id`),
  CONSTRAINT `FK9t82oibyduo62wci8y6gfpllx` FOREIGN KEY (`id`) REFERENCES `articulo` (`id`),
  CONSTRAINT `FKq0sulc6jjw51efw0g1yf9cvs2` FOREIGN KEY (`sucursal_id`) REFERENCES `sucursal` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `articulo_manufacturado`
--

LOCK TABLES `articulo_manufacturado` WRITE;
/*!40000 ALTER TABLE `articulo_manufacturado` DISABLE KEYS */;
INSERT INTO `articulo_manufacturado` VALUES ('Cocinar la carne, tostar el pan, agregar vegetales y armar la hamburguesa','15',1,1),('Algo','21',19,1),('Se ponen los tacos...','25',20,1),('Afafafa','15',22,1);
/*!40000 ALTER TABLE `articulo_manufacturado` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `articulo_manufacturado_detalle_insumo`
--

DROP TABLE IF EXISTS `articulo_manufacturado_detalle_insumo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `articulo_manufacturado_detalle_insumo` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `cantidad` int DEFAULT NULL,
  `id_articulo_insumo` bigint DEFAULT NULL,
  `id_articulo_manufacturado` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKm9fxklpbn2itftdj349wkblpl` (`id_articulo_insumo`),
  KEY `FK9o8tdc70urx95p1gs1a63uld4` (`id_articulo_manufacturado`),
  CONSTRAINT `FK9o8tdc70urx95p1gs1a63uld4` FOREIGN KEY (`id_articulo_manufacturado`) REFERENCES `articulo_manufacturado` (`id`),
  CONSTRAINT `FKm9fxklpbn2itftdj349wkblpl` FOREIGN KEY (`id_articulo_insumo`) REFERENCES `articulo_insumo` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `articulo_manufacturado_detalle_insumo`
--

LOCK TABLES `articulo_manufacturado_detalle_insumo` WRITE;
/*!40000 ALTER TABLE `articulo_manufacturado_detalle_insumo` DISABLE KEYS */;
INSERT INTO `articulo_manufacturado_detalle_insumo` VALUES (15,2,2,1),(16,3,3,1),(18,200,4,19),(19,2,5,19),(20,3,2,20),(21,2,3,20),(22,2,3,22),(23,200,4,22);
/*!40000 ALTER TABLE `articulo_manufacturado_detalle_insumo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categoria`
--

DROP TABLE IF EXISTS `categoria`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categoria` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `denominacion` varchar(255) DEFAULT NULL,
  `imagen` varchar(255) DEFAULT NULL,
  `es_para_elaborar` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categoria`
--

LOCK TABLES `categoria` WRITE;
/*!40000 ALTER TABLE `categoria` DISABLE KEYS */;
INSERT INTO `categoria` VALUES (1,'Rapida','categoriaPizza.jpg',_binary '\0'),(2,'Bebidas','categoriaBebidas.jpg',_binary '\0');
/*!40000 ALTER TABLE `categoria` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ciudad`
--

DROP TABLE IF EXISTS `ciudad`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ciudad` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) DEFAULT NULL,
  `provincia_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK8pvpi9wl4uebdop190wbj3jyp` (`provincia_id`),
  CONSTRAINT `FK8pvpi9wl4uebdop190wbj3jyp` FOREIGN KEY (`provincia_id`) REFERENCES `provincia` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ciudad`
--

LOCK TABLES `ciudad` WRITE;
/*!40000 ALTER TABLE `ciudad` DISABLE KEYS */;
INSERT INTO `ciudad` VALUES (1,'Ciudad de Mendoza',1),(2,'Las Heras',1),(3,'Godoy Cruz',1);
/*!40000 ALTER TABLE `ciudad` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `detalle_pedido`
--

DROP TABLE IF EXISTS `detalle_pedido`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `detalle_pedido` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `cantidad` int DEFAULT NULL,
  `id_articulo` bigint DEFAULT NULL,
  `id_pedido` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKmmgw1ju1dfci3scs40te1y4bi` (`id_articulo`),
  KEY `FK7n9hdifr08joboojejveby1vr` (`id_pedido`),
  CONSTRAINT `FK7n9hdifr08joboojejveby1vr` FOREIGN KEY (`id_pedido`) REFERENCES `pedido` (`id`),
  CONSTRAINT `FKmmgw1ju1dfci3scs40te1y4bi` FOREIGN KEY (`id_articulo`) REFERENCES `articulo` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `detalle_pedido`
--

LOCK TABLES `detalle_pedido` WRITE;
/*!40000 ALTER TABLE `detalle_pedido` DISABLE KEYS */;
INSERT INTO `detalle_pedido` VALUES (21,1,6,20),(22,1,6,21),(23,1,19,20),(24,1,19,21),(25,1,19,23),(26,1,19,22),(27,1,6,22),(28,1,6,23);
/*!40000 ALTER TABLE `detalle_pedido` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `detalle_promocion`
--

DROP TABLE IF EXISTS `detalle_promocion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `detalle_promocion` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `cantidad` int DEFAULT NULL,
  `pedido_id` bigint DEFAULT NULL,
  `promocion_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKein7mr5sjxan16l9k5tfdyhie` (`pedido_id`),
  KEY `FKc7mv7i24hoyl49rof6yq8dhsj` (`promocion_id`),
  CONSTRAINT `FKc7mv7i24hoyl49rof6yq8dhsj` FOREIGN KEY (`promocion_id`) REFERENCES `promocion` (`id`),
  CONSTRAINT `FKein7mr5sjxan16l9k5tfdyhie` FOREIGN KEY (`pedido_id`) REFERENCES `pedido` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `detalle_promocion`
--

LOCK TABLES `detalle_promocion` WRITE;
/*!40000 ALTER TABLE `detalle_promocion` DISABLE KEYS */;
INSERT INTO `detalle_promocion` VALUES (1,1,18,2),(2,1,19,2),(3,1,22,1),(4,1,23,1);
/*!40000 ALTER TABLE `detalle_promocion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `direccion`
--

DROP TABLE IF EXISTS `direccion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `direccion` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `alias` varchar(255) DEFAULT NULL,
  `descripcion_entrega` text,
  `latitud` double DEFAULT NULL,
  `longitud` double DEFAULT NULL,
  `nombre_calle` varchar(255) DEFAULT NULL,
  `numeracion` varchar(255) DEFAULT NULL,
  `id_ciudad` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKghplm1o00tae0g7535s2jexa8` (`id_ciudad`),
  CONSTRAINT `FKghplm1o00tae0g7535s2jexa8` FOREIGN KEY (`id_ciudad`) REFERENCES `ciudad` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `direccion`
--

LOCK TABLES `direccion` WRITE;
/*!40000 ALTER TABLE `direccion` DISABLE KEYS */;
INSERT INTO `direccion` VALUES (1,'Oficina Principal','Frente a la Plaza San Martín, edificio color crema.',-32.890692,-68.847145,'Av. San Martin','1100',1),(2,'Apartamento Mendoza','Tocar timbre, departamento 3B.',-32.8943,-68.8529,'Arístides Villanueva','450',2),(3,'Oficina Centro','Dejar en recepción, preguntar por Martín.',-32.8879,-68.8631,'Emilio Civit','800',1);
/*!40000 ALTER TABLE `direccion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `direccion_pedido`
--

DROP TABLE IF EXISTS `direccion_pedido`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `direccion_pedido` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `id_direccion` bigint DEFAULT NULL,
  `id_pedido` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKpyvs19nytohcgn0qbcdwhkl7r` (`id_pedido`),
  KEY `FKe2dr72ar954n0ykvgkc8gld5j` (`id_direccion`),
  CONSTRAINT `FKe2dr72ar954n0ykvgkc8gld5j` FOREIGN KEY (`id_direccion`) REFERENCES `direccion` (`id`),
  CONSTRAINT `FKi04ryieno87fpfx09rwtviegc` FOREIGN KEY (`id_pedido`) REFERENCES `pedido` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `direccion_pedido`
--

LOCK TABLES `direccion_pedido` WRITE;
/*!40000 ALTER TABLE `direccion_pedido` DISABLE KEYS */;
/*!40000 ALTER TABLE `direccion_pedido` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `empleado`
--

DROP TABLE IF EXISTS `empleado`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `empleado` (
  `fecha_alta` datetime(6) DEFAULT NULL,
  `sueldo` double DEFAULT NULL,
  `id` bigint NOT NULL,
  `id_sucursal` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK5lshn105gw8o1gcw78wysko3u` (`id_sucursal`),
  CONSTRAINT `FK21ddfrduanruae8nw4y1gbfw` FOREIGN KEY (`id`) REFERENCES `usuario` (`id`),
  CONSTRAINT `FK5lshn105gw8o1gcw78wysko3u` FOREIGN KEY (`id_sucursal`) REFERENCES `sucursal` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `empleado`
--

LOCK TABLES `empleado` WRITE;
/*!40000 ALTER TABLE `empleado` DISABLE KEYS */;
/*!40000 ALTER TABLE `empleado` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `empresa`
--

DROP TABLE IF EXISTS `empresa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `empresa` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `cuil` varchar(255) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `razon_social` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `empresa`
--

LOCK TABLES `empresa` WRITE;
/*!40000 ALTER TABLE `empresa` DISABLE KEYS */;
INSERT INTO `empresa` VALUES (1,'1234','Buen Sabor','Buen Sabor');
/*!40000 ALTER TABLE `empresa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `estado_pedido`
--

DROP TABLE IF EXISTS `estado_pedido`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `estado_pedido` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nombre_estado` enum('CANCELLED','DELIVERED','INCOMING','PREPARING','REJECTED','STANDBY','READY') DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `estado_pedido`
--

LOCK TABLES `estado_pedido` WRITE;
/*!40000 ALTER TABLE `estado_pedido` DISABLE KEYS */;
INSERT INTO `estado_pedido` VALUES (1,'PREPARING'),(2,'STANDBY'),(3,'CANCELLED'),(4,'REJECTED'),(5,'INCOMING'),(6,'DELIVERED'),(7,'READY');
/*!40000 ALTER TABLE `estado_pedido` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `historico_precio_costo_articulo_insumo`
--

DROP TABLE IF EXISTS `historico_precio_costo_articulo_insumo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `historico_precio_costo_articulo_insumo` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `fecha` date DEFAULT NULL,
  `precio` double DEFAULT NULL,
  `id_articulo_insumo` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKfikd1rprk6iw3b946cmjhw992` (`id_articulo_insumo`),
  CONSTRAINT `FKfikd1rprk6iw3b946cmjhw992` FOREIGN KEY (`id_articulo_insumo`) REFERENCES `articulo_insumo` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `historico_precio_costo_articulo_insumo`
--

LOCK TABLES `historico_precio_costo_articulo_insumo` WRITE;
/*!40000 ALTER TABLE `historico_precio_costo_articulo_insumo` DISABLE KEYS */;
/*!40000 ALTER TABLE `historico_precio_costo_articulo_insumo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `historico_precio_venta_articulo`
--

DROP TABLE IF EXISTS `historico_precio_venta_articulo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `historico_precio_venta_articulo` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `fecha` date DEFAULT NULL,
  `precio` double DEFAULT NULL,
  `id_articulo` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKc4i89a6hnlpq2mhfeg6xw1w5k` (`id_articulo`),
  CONSTRAINT `FKc4i89a6hnlpq2mhfeg6xw1w5k` FOREIGN KEY (`id_articulo`) REFERENCES `articulo` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `historico_precio_venta_articulo`
--

LOCK TABLES `historico_precio_venta_articulo` WRITE;
/*!40000 ALTER TABLE `historico_precio_venta_articulo` DISABLE KEYS */;
/*!40000 ALTER TABLE `historico_precio_venta_articulo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `historico_stock_articulo_insumo`
--

DROP TABLE IF EXISTS `historico_stock_articulo_insumo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `historico_stock_articulo_insumo` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `cantidad` int DEFAULT NULL,
  `fecha_actualizacion` datetime(6) DEFAULT NULL,
  `id_stock_articulo_insumo` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK5ya2qw6ebfpg6mulswt01r8ys` (`id_stock_articulo_insumo`),
  CONSTRAINT `FK5ya2qw6ebfpg6mulswt01r8ys` FOREIGN KEY (`id_stock_articulo_insumo`) REFERENCES `stock_articulo_insumo` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `historico_stock_articulo_insumo`
--

LOCK TABLES `historico_stock_articulo_insumo` WRITE;
/*!40000 ALTER TABLE `historico_stock_articulo_insumo` DISABLE KEYS */;
/*!40000 ALTER TABLE `historico_stock_articulo_insumo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mercado_pago`
--

DROP TABLE IF EXISTS `mercado_pago`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mercado_pago` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `mp_merchant_order_id` int DEFAULT NULL,
  `mp_payment_type` varchar(255) DEFAULT NULL,
  `mp_preference_id` varchar(255) DEFAULT NULL,
  `tipo_pago_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKmibxllnei07l0cq4n8rhop26w` (`tipo_pago_id`),
  CONSTRAINT `FKmibxllnei07l0cq4n8rhop26w` FOREIGN KEY (`tipo_pago_id`) REFERENCES `tipo_pago` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mercado_pago`
--

LOCK TABLES `mercado_pago` WRITE;
/*!40000 ALTER TABLE `mercado_pago` DISABLE KEYS */;
/*!40000 ALTER TABLE `mercado_pago` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pais`
--

DROP TABLE IF EXISTS `pais`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pais` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pais`
--

LOCK TABLES `pais` WRITE;
/*!40000 ALTER TABLE `pais` DISABLE KEYS */;
INSERT INTO `pais` VALUES (1,'Argentina');
/*!40000 ALTER TABLE `pais` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pedido`
--

DROP TABLE IF EXISTS `pedido`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pedido` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `existe` bit(1) DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `tiempo_estimado` varchar(255) DEFAULT NULL,
  `id_estado_pedido` bigint DEFAULT NULL,
  `id_sucursal` bigint DEFAULT NULL,
  `id_tipo_envio` bigint DEFAULT NULL,
  `id_tipo_pago` bigint DEFAULT NULL,
  `id_cliente` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKpwts0xmsajvn0pjcukb14hpih` (`id_estado_pedido`),
  KEY `FKqcehlhy9unki4432xwv6q1ui` (`id_sucursal`),
  KEY `FK40rb56ko0e5swoycyojaxbqie` (`id_tipo_envio`),
  KEY `FKnehsi9fw9h4l4go0hpywfos3h` (`id_tipo_pago`),
  KEY `FK48929k2o7euot8lws254925op` (`id_cliente`),
  CONSTRAINT `FK40rb56ko0e5swoycyojaxbqie` FOREIGN KEY (`id_tipo_envio`) REFERENCES `tipo_envio` (`id`),
  CONSTRAINT `FK48929k2o7euot8lws254925op` FOREIGN KEY (`id_cliente`) REFERENCES `usuario` (`id`),
  CONSTRAINT `FKnehsi9fw9h4l4go0hpywfos3h` FOREIGN KEY (`id_tipo_pago`) REFERENCES `tipo_pago` (`id`),
  CONSTRAINT `FKpwts0xmsajvn0pjcukb14hpih` FOREIGN KEY (`id_estado_pedido`) REFERENCES `estado_pedido` (`id`),
  CONSTRAINT `FKqcehlhy9unki4432xwv6q1ui` FOREIGN KEY (`id_sucursal`) REFERENCES `sucursal` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pedido`
--

LOCK TABLES `pedido` WRITE;
/*!40000 ALTER TABLE `pedido` DISABLE KEYS */;
INSERT INTO `pedido` VALUES (18,_binary '','2025-06-13','30',5,1,2,1,2),(19,_binary '','2025-06-13','30',5,1,2,1,2),(20,_binary '','2025-06-13','21',5,1,2,1,2),(21,_binary '','2025-06-13','21',5,1,2,1,2),(22,_binary '','2025-06-13','30',5,1,2,1,2),(23,_binary '','2025-06-13','30',5,1,2,1,2);
/*!40000 ALTER TABLE `pedido` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `promocion`
--

DROP TABLE IF EXISTS `promocion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `promocion` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `denominacion` varchar(255) DEFAULT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `existe` bit(1) DEFAULT NULL,
  `precio_rebajado` double DEFAULT NULL,
  `id_tipo_promocion` bigint DEFAULT NULL,
  `sucursal_id` bigint DEFAULT NULL,
  `imagen` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKl9rh665bbmqohkvkhmbpveofh` (`id_tipo_promocion`),
  KEY `FKaulen6od3ay1qnp7smhlxwr41` (`sucursal_id`),
  CONSTRAINT `FKaulen6od3ay1qnp7smhlxwr41` FOREIGN KEY (`sucursal_id`) REFERENCES `sucursal` (`id`),
  CONSTRAINT `FKl9rh665bbmqohkvkhmbpveofh` FOREIGN KEY (`id_tipo_promocion`) REFERENCES `tipo_promocion` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `promocion`
--

LOCK TABLES `promocion` WRITE;
/*!40000 ALTER TABLE `promocion` DISABLE KEYS */;
INSERT INTO `promocion` VALUES (1,'Promo 1','2 hamburguesas clasicas + 2 coca-cola de 500ml',_binary '',16000,1,1,'promo1.jpg'),(2,'Promo 2','2 hamburguesas clasicas + 2 coca-cola de 500ml',_binary '',16000,1,1,'hamburguesa.jpg');
/*!40000 ALTER TABLE `promocion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `promocion_articulo`
--

DROP TABLE IF EXISTS `promocion_articulo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `promocion_articulo` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `cantidad` int NOT NULL,
  `id_articulo` bigint DEFAULT NULL,
  `id_promocion` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK4y7ryw28mhcsv4l5yfom1vacc` (`id_articulo`),
  KEY `FKjwmjq15gq1b3ft75sbq2ointf` (`id_promocion`),
  CONSTRAINT `FK4y7ryw28mhcsv4l5yfom1vacc` FOREIGN KEY (`id_articulo`) REFERENCES `articulo` (`id`),
  CONSTRAINT `FKjwmjq15gq1b3ft75sbq2ointf` FOREIGN KEY (`id_promocion`) REFERENCES `promocion` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `promocion_articulo`
--

LOCK TABLES `promocion_articulo` WRITE;
/*!40000 ALTER TABLE `promocion_articulo` DISABLE KEYS */;
INSERT INTO `promocion_articulo` VALUES (1,2,1,1),(2,2,6,1);
/*!40000 ALTER TABLE `promocion_articulo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `provincia`
--

DROP TABLE IF EXISTS `provincia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `provincia` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) DEFAULT NULL,
  `pais_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKm4s599988w0v1q1nw6dyo5t2m` (`pais_id`),
  CONSTRAINT `FKm4s599988w0v1q1nw6dyo5t2m` FOREIGN KEY (`pais_id`) REFERENCES `pais` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `provincia`
--

LOCK TABLES `provincia` WRITE;
/*!40000 ALTER TABLE `provincia` DISABLE KEYS */;
INSERT INTO `provincia` VALUES (1,'Mendoza',1);
/*!40000 ALTER TABLE `provincia` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `revision_info`
--

DROP TABLE IF EXISTS `revision_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `revision_info` (
  `id` bigint NOT NULL,
  `revision_date` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `revision_info`
--

LOCK TABLES `revision_info` WRITE;
/*!40000 ALTER TABLE `revision_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `revision_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rol`
--

DROP TABLE IF EXISTS `rol`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rol` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `fecha_alta` datetime(6) DEFAULT NULL,
  `tipo_rol_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKq30vjy293hs1xwh58j1xk11f6` (`tipo_rol_id`),
  CONSTRAINT `FKq30vjy293hs1xwh58j1xk11f6` FOREIGN KEY (`tipo_rol_id`) REFERENCES `tipo_rol` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rol`
--

LOCK TABLES `rol` WRITE;
/*!40000 ALTER TABLE `rol` DISABLE KEYS */;
INSERT INTO `rol` VALUES (1,'2024-06-12 19:30:00.000000',4);
/*!40000 ALTER TABLE `rol` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `seq_revision_id`
--

DROP TABLE IF EXISTS `seq_revision_id`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `seq_revision_id` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `seq_revision_id`
--

LOCK TABLES `seq_revision_id` WRITE;
/*!40000 ALTER TABLE `seq_revision_id` DISABLE KEYS */;
INSERT INTO `seq_revision_id` VALUES (1);
/*!40000 ALTER TABLE `seq_revision_id` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stock_articulo_insumo`
--

DROP TABLE IF EXISTS `stock_articulo_insumo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `stock_articulo_insumo` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `cantidad` int DEFAULT NULL,
  `max_stock` int DEFAULT NULL,
  `id_articulo_insumo` bigint DEFAULT NULL,
  `id_sucursal` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK5j6tvq49t9gu42bys3w7vflk5` (`id_articulo_insumo`),
  KEY `FKq5f29058r2ggd91ouu4j9ru0m` (`id_sucursal`),
  CONSTRAINT `FK5j6tvq49t9gu42bys3w7vflk5` FOREIGN KEY (`id_articulo_insumo`) REFERENCES `articulo_insumo` (`id`),
  CONSTRAINT `FKq5f29058r2ggd91ouu4j9ru0m` FOREIGN KEY (`id_sucursal`) REFERENCES `sucursal` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stock_articulo_insumo`
--

LOCK TABLES `stock_articulo_insumo` WRITE;
/*!40000 ALTER TABLE `stock_articulo_insumo` DISABLE KEYS */;
/*!40000 ALTER TABLE `stock_articulo_insumo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subcategoria`
--

DROP TABLE IF EXISTS `subcategoria`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `subcategoria` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `denominacion` varchar(255) DEFAULT NULL,
  `id_categoria` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK5n9f7pm966nyw6mue7994u3bl` (`id_categoria`),
  CONSTRAINT `FK5n9f7pm966nyw6mue7994u3bl` FOREIGN KEY (`id_categoria`) REFERENCES `categoria` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subcategoria`
--

LOCK TABLES `subcategoria` WRITE;
/*!40000 ALTER TABLE `subcategoria` DISABLE KEYS */;
INSERT INTO `subcategoria` VALUES (1,'Hamburguesas',1),(2,'Bebidas',2),(3,'Ingredientes',1);
/*!40000 ALTER TABLE `subcategoria` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sucursal`
--

DROP TABLE IF EXISTS `sucursal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sucursal` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `existe` bit(1) DEFAULT NULL,
  `hora_apertura` varchar(255) DEFAULT NULL,
  `hora_cierre` varchar(255) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `direccion_id` bigint DEFAULT NULL,
  `empresa_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKl4uaatssksxv3p4gao7q3yx1q` (`direccion_id`),
  KEY `FK3w56rbjykxbp2e79cdq0xsghd` (`empresa_id`),
  CONSTRAINT `FK3w56rbjykxbp2e79cdq0xsghd` FOREIGN KEY (`empresa_id`) REFERENCES `empresa` (`id`),
  CONSTRAINT `FKk4e07r5ywhsi8klk9la7u00mw` FOREIGN KEY (`direccion_id`) REFERENCES `direccion` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sucursal`
--

LOCK TABLES `sucursal` WRITE;
/*!40000 ALTER TABLE `sucursal` DISABLE KEYS */;
INSERT INTO `sucursal` VALUES (1,_binary '','20:30','00:00','Sucursal Principal',1,1);
/*!40000 ALTER TABLE `sucursal` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `telefono`
--

DROP TABLE IF EXISTS `telefono`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `telefono` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `numero` bigint DEFAULT NULL,
  `usuario_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKpi2c7iq0lw09d1ovc7bn86f85` (`usuario_id`),
  CONSTRAINT `FKpi2c7iq0lw09d1ovc7bn86f85` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `telefono`
--

LOCK TABLES `telefono` WRITE;
/*!40000 ALTER TABLE `telefono` DISABLE KEYS */;
INSERT INTO `telefono` VALUES (1,2614567890,2),(2,2615123456,2);
/*!40000 ALTER TABLE `telefono` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipo_envio`
--

DROP TABLE IF EXISTS `tipo_envio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tipo_envio` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nombre_envio` enum('DELIVERY','TAKEAWAY') DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipo_envio`
--

LOCK TABLES `tipo_envio` WRITE;
/*!40000 ALTER TABLE `tipo_envio` DISABLE KEYS */;
INSERT INTO `tipo_envio` VALUES (1,'DELIVERY'),(2,'TAKEAWAY');
/*!40000 ALTER TABLE `tipo_envio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipo_pago`
--

DROP TABLE IF EXISTS `tipo_pago`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tipo_pago` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nombre_pago` enum('CASH','MERCADOPAGO') DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipo_pago`
--

LOCK TABLES `tipo_pago` WRITE;
/*!40000 ALTER TABLE `tipo_pago` DISABLE KEYS */;
INSERT INTO `tipo_pago` VALUES (1,'CASH'),(2,'MERCADOPAGO');
/*!40000 ALTER TABLE `tipo_pago` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipo_promocion`
--

DROP TABLE IF EXISTS `tipo_promocion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tipo_promocion` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `tipo` enum('BIRTHDAY','FEST','HAPPYHOUR','NORMAL','WORKER') DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipo_promocion`
--

LOCK TABLES `tipo_promocion` WRITE;
/*!40000 ALTER TABLE `tipo_promocion` DISABLE KEYS */;
INSERT INTO `tipo_promocion` VALUES (1,'NORMAL'),(2,'HAPPYHOUR'),(3,'FEST'),(4,'BIRTHDAY'),(5,'WORKER');
/*!40000 ALTER TABLE `tipo_promocion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipo_rol`
--

DROP TABLE IF EXISTS `tipo_rol`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tipo_rol` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nombre_rol` enum('ADMIN','ADMINAREA','CUSTOMER','EMPLOYEE') DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipo_rol`
--

LOCK TABLES `tipo_rol` WRITE;
/*!40000 ALTER TABLE `tipo_rol` DISABLE KEYS */;
INSERT INTO `tipo_rol` VALUES (1,'ADMIN'),(2,'ADMINAREA'),(3,'EMPLOYEE'),(4,'CUSTOMER');
/*!40000 ALTER TABLE `tipo_rol` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `unidad_medida`
--

DROP TABLE IF EXISTS `unidad_medida`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `unidad_medida` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `unidad` enum('gr','ml','unidad') DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `unidad_medida`
--

LOCK TABLES `unidad_medida` WRITE;
/*!40000 ALTER TABLE `unidad_medida` DISABLE KEYS */;
INSERT INTO `unidad_medida` VALUES (1,'unidad'),(2,'gr'),(3,'ml');
/*!40000 ALTER TABLE `unidad_medida` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_authentication`
--

DROP TABLE IF EXISTS `user_authentication`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_authentication` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_authentication`
--

LOCK TABLES `user_authentication` WRITE;
/*!40000 ALTER TABLE `user_authentication` DISABLE KEYS */;
INSERT INTO `user_authentication` VALUES (1,'hashed_password_martin','martin.rodriguez');
/*!40000 ALTER TABLE `user_authentication` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `apellido` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `existe` bit(1) DEFAULT NULL,
  `imagen_usuario` varchar(255) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `id_rol` bigint DEFAULT NULL,
  `id_user_auth` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK48l1t6o09n85e7r6wpspg9p59` (`id_user_auth`),
  KEY `FKmyv3138vvci6kaq3y5kt4cntu` (`id_rol`),
  CONSTRAINT `FKbbg6v1ugiutw86qvyihganbsx` FOREIGN KEY (`id_user_auth`) REFERENCES `user_authentication` (`id`),
  CONSTRAINT `FKmyv3138vvci6kaq3y5kt4cntu` FOREIGN KEY (`id_rol`) REFERENCES `rol` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (2,'Rodríguez','martin.rodriguez@example.com',_binary '','miniUsuario.jpg','Martín',1,1);
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario_direccion`
--

DROP TABLE IF EXISTS `usuario_direccion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario_direccion` (
  `id_usuario` bigint NOT NULL,
  `id_direccion` bigint NOT NULL,
  KEY `FKh84fo3kgjmb0sw1jnvtxr0j16` (`id_direccion`),
  KEY `FKi86s0htd0xa3rda9vp7xp8pxc` (`id_usuario`),
  CONSTRAINT `FKh84fo3kgjmb0sw1jnvtxr0j16` FOREIGN KEY (`id_direccion`) REFERENCES `direccion` (`id`),
  CONSTRAINT `FKi86s0htd0xa3rda9vp7xp8pxc` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario_direccion`
--

LOCK TABLES `usuario_direccion` WRITE;
/*!40000 ALTER TABLE `usuario_direccion` DISABLE KEYS */;
INSERT INTO `usuario_direccion` VALUES (2,2),(2,3);
/*!40000 ALTER TABLE `usuario_direccion` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-06-16  0:13:31
