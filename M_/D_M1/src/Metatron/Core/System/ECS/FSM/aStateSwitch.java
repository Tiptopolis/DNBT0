package Metatron.Core.System.ECS.FSM;

import Metatron.Core.Primitive.aNode;
import Metatron.Core.Primitive.iFunctor;
import Metatron.Core.Primitive.iFunctor.Predicate;
import Metatron.Core.Primitive.A_I.iComponent;
import Metatron.Core.Primitive.Struct.aMultiMap;

public class aStateSwitch implements iComponent {

	public aNode owner;
	public aMultiMap<iFunctor, Predicate> ifDo;// [CaseStatement|Predicate] mapping

	@Override
	public aNode getOwner() {
		return this.owner;
	}

	@Override
	public void setOwner(aNode owner) {
		this.owner = owner;

	}
}
