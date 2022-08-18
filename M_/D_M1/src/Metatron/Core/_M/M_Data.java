package Metatron.Core._M;

import java.sql.Connection;

import Metatron.Core.Primitive.Data.aTable;
import Metatron.Core.Primitive.Struct.aDictionary;

public class M_Data {

	public aDictionary<Connection> DB; // <Domain|EndPT>
	public aDictionary<aTable> Data;

}
