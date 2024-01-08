-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 08, 2024 at 07:29 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `laper_ah`
--

-- --------------------------------------------------------

--
-- Table structure for table `branch`
--

CREATE TABLE `branch` (
  `BranchID` int(11) NOT NULL,
  `Location` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `branch`
--

INSERT INTO `branch` (`BranchID`, `Location`) VALUES
(1, 'Bandung'),
(2, 'Jakarta'),
(3, 'Bali'),
(4, 'Surabaya'),
(5, 'Samarinda'),
(6, 'Padang');

-- --------------------------------------------------------

--
-- Table structure for table `employee`
--

CREATE TABLE `employee` (
  `EmployeeID` int(11) NOT NULL,
  `BranchID` int(11) NOT NULL,
  `EmployeeName` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `employee`
--

INSERT INTO `employee` (`EmployeeID`, `BranchID`, `EmployeeName`) VALUES
(1, 2, 'Putu Angga'),
(2, 4, 'Budi Anjayani'),
(3, 6, 'Joko Santoso'),
(4, 1, 'Kape Peka');

-- --------------------------------------------------------

--
-- Table structure for table `menu`
--

CREATE TABLE `menu` (
  `MenuID` int(11) NOT NULL,
  `MenuName` varchar(50) NOT NULL,
  `MenuType` varchar(10) NOT NULL,
  `MenuPrice` int(11) NOT NULL,
  `Origin` varchar(20) DEFAULT NULL,
  `MenuDescription` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `menu`
--

INSERT INTO `menu` (`MenuID`, `MenuName`, `MenuType`, `MenuPrice`, `Origin`, `MenuDescription`) VALUES
(1, 'Nasi Putih', 'General', 6000, NULL, NULL),
(2, 'Telor Mata Sapi', 'General', 5000, NULL, NULL),
(3, 'Nasi Padang', 'Local', 29000, 'Padang', 'Makanan khas daerah Sumatera barat ini dari nasi putih yang disajikan dengan berbagai macam lauk pauk, seperti rendang, gulai, ayam goreng, dan lain-lain. Nasi Padang biasanya disajikan dengan sambal dan kerupuk.'),
(4, 'Caviar', 'Special', 1000000, NULL, 'Telur ikan sturgeon yang diasinkan, disajikan dingin. Tekstur lembut dan creamy, rasa gurih dan asin yang khas. Cocok sebagai hidangan pembuka atau pelengkap.');

-- --------------------------------------------------------

--
-- Table structure for table `reservation`
--

CREATE TABLE `reservation` (
  `ReservationID` int(11) NOT NULL,
  `TableID` int(11) NOT NULL,
  `EmployeeID` int(11) NOT NULL,
  `CustomerName` varchar(50) NOT NULL,
  `Status` varchar(12) NOT NULL,
  `NumberOfCustomers` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `reservation`
--

INSERT INTO `reservation` (`ReservationID`, `TableID`, `EmployeeID`, `CustomerName`, `Status`, `NumberOfCustomers`) VALUES
(1, 1, 1, 'Nurhasana Hanan', 'Finalized', 2),
(2, 3, 4, 'Andi Dian', 'In Reserve', 7),
(3, 2, 3, 'Indra Ardi', 'Finalized', 4);

-- --------------------------------------------------------

--
-- Table structure for table `restauranttable`
--

CREATE TABLE `restauranttable` (
  `TableID` int(11) NOT NULL,
  `BranchID` int(11) NOT NULL,
  `TableTypeID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `restauranttable`
--

INSERT INTO `restauranttable` (`TableID`, `BranchID`, `TableTypeID`) VALUES
(1, 2, 1),
(2, 4, 3),
(3, 1, 2),
(4, 6, 1);

-- --------------------------------------------------------

--
-- Table structure for table `tabletype`
--

CREATE TABLE `tabletype` (
  `TableTypeID` int(11) NOT NULL,
  `Type` varchar(10) NOT NULL,
  `MaxCapacity` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tabletype`
--

INSERT INTO `tabletype` (`TableTypeID`, `Type`, `MaxCapacity`) VALUES
(1, 'Romantic', 2),
(2, 'General', 4),
(3, 'Family', 10);

-- --------------------------------------------------------

--
-- Table structure for table `transaction`
--

CREATE TABLE `transaction` (
  `TransactionID` int(11) NOT NULL,
  `ReservationID` int(11) NOT NULL,
  `MenuID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `transaction`
--

INSERT INTO `transaction` (`TransactionID`, `ReservationID`, `MenuID`) VALUES
(1, 1, 4),
(2, 2, 2),
(3, 3, 3);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `branch`
--
ALTER TABLE `branch`
  ADD PRIMARY KEY (`BranchID`);

--
-- Indexes for table `employee`
--
ALTER TABLE `employee`
  ADD PRIMARY KEY (`EmployeeID`),
  ADD KEY `branchidemployeetforeign` (`BranchID`);

--
-- Indexes for table `menu`
--
ALTER TABLE `menu`
  ADD PRIMARY KEY (`MenuID`);

--
-- Indexes for table `reservation`
--
ALTER TABLE `reservation`
  ADD PRIMARY KEY (`ReservationID`),
  ADD KEY `TableIDReservationForeign` (`TableID`),
  ADD KEY `EmployeeIDReservationForeign` (`EmployeeID`);

--
-- Indexes for table `restauranttable`
--
ALTER TABLE `restauranttable`
  ADD PRIMARY KEY (`TableID`),
  ADD KEY `branchidforeign` (`BranchID`),
  ADD KEY `Tabletypeidforeign` (`TableTypeID`);

--
-- Indexes for table `tabletype`
--
ALTER TABLE `tabletype`
  ADD PRIMARY KEY (`TableTypeID`);

--
-- Indexes for table `transaction`
--
ALTER TABLE `transaction`
  ADD PRIMARY KEY (`TransactionID`),
  ADD KEY `ReservationIDTransactionForeign` (`ReservationID`),
  ADD KEY `MenuIDTransactionForeign` (`MenuID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `branch`
--
ALTER TABLE `branch`
  MODIFY `BranchID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `employee`
--
ALTER TABLE `employee`
  MODIFY `EmployeeID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `menu`
--
ALTER TABLE `menu`
  MODIFY `MenuID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `reservation`
--
ALTER TABLE `reservation`
  MODIFY `ReservationID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `restauranttable`
--
ALTER TABLE `restauranttable`
  MODIFY `TableID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `tabletype`
--
ALTER TABLE `tabletype`
  MODIFY `TableTypeID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `transaction`
--
ALTER TABLE `transaction`
  MODIFY `TransactionID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `employee`
--
ALTER TABLE `employee`
  ADD CONSTRAINT `branchidemployeetforeign` FOREIGN KEY (`BranchID`) REFERENCES `branch` (`BranchID`);

--
-- Constraints for table `reservation`
--
ALTER TABLE `reservation`
  ADD CONSTRAINT `EmployeeIDReservationForeign` FOREIGN KEY (`EmployeeID`) REFERENCES `employee` (`EmployeeID`),
  ADD CONSTRAINT `TableIDReservationForeign` FOREIGN KEY (`TableID`) REFERENCES `restauranttable` (`TableID`);

--
-- Constraints for table `restauranttable`
--
ALTER TABLE `restauranttable`
  ADD CONSTRAINT `Tabletypeidforeign` FOREIGN KEY (`TableTypeID`) REFERENCES `tabletype` (`TableTypeID`),
  ADD CONSTRAINT `branchidforeign` FOREIGN KEY (`BranchID`) REFERENCES `branch` (`BranchID`);

--
-- Constraints for table `transaction`
--
ALTER TABLE `transaction`
  ADD CONSTRAINT `MenuIDTransactionForeign` FOREIGN KEY (`MenuID`) REFERENCES `menu` (`MenuID`),
  ADD CONSTRAINT `ReservationIDTransactionForeign` FOREIGN KEY (`ReservationID`) REFERENCES `reservation` (`ReservationID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
