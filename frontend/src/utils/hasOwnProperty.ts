export const hasOwnProperty = <ObjectType extends object, KeyType extends PropertyKey>(
  obj: ObjectType,
  prop: KeyType,
): obj is ObjectType & Record<KeyType, unknown> => obj.hasOwnProperty(prop);
