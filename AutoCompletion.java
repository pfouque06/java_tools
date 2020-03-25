package javaTools;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class AutoCompletion<T> {

    // const
    private static final long MAX_CANDIDATE_NUMBERS_WITHOUT_PREFIX = 5;
    private static final long MAX_CANDIDATE_NUMBERS = 5;

    private Class<T> type;
    private String candidateStringField;
    private String candidateOccurrenceField;
    private OccurrenceFieldType candidateOccurrenceFieldType;
    private enum OccurrenceFieldType {collection, number, primitive, other};
    //private OccurrenceFieldNumberType candidateOccurrenceFieldNumberType;
    //private enum OccurrenceFieldNumberType { INTEGER, LONG};
    private long maxCandidateNumbersFull;
    private long maxCandidateNumbers;

    public AutoCompletion(Class<T> classType, String candidatStringField, String candidateOccurrenceField, long... maxCandidates) {
        //this(classType, candidatStringField, candidateOccurrenceField);
        super();

        System.out.println(">>> START AutoCompletion(classType: " + classType.getSimpleName() +
                ", candidatStringField: " + candidatStringField +
                ", candidateOccurrenceField: " + candidateOccurrenceField + ")");
        this.type = classType;
        this.candidateStringField = candidatStringField;
        this.candidateOccurrenceField = candidateOccurrenceField;
        this.maxCandidateNumbers = maxCandidates.length > 0 ? maxCandidates[0] : MAX_CANDIDATE_NUMBERS;
        this.maxCandidateNumbersFull = maxCandidates.length > 1 ? maxCandidates[1] : MAX_CANDIDATE_NUMBERS_WITHOUT_PREFIX;
        System.out.println(">>> maxCandidateNumbers: " + this.maxCandidateNumbers + ", maxCandidateNumbersFull: " + this.maxCandidateNumbersFull);

        // unvalidate type if fields cannot be validated
        if (!validateFields()) {
            type = null;
        }
    }

    public List<T> findCandidates(List<T> inputList, String pMatch) {

        // check that instance is validated
        if (type == null)
            return null;
        System.out.println(">>> START AutoCompletion.findCandidates<" + type.getSimpleName() + "> (match: " + pMatch + ")");

        // lowering match
        String match = pMatch != null ? pMatch.toLowerCase() : "";

        // looking for completion candidates : Java 8 code
        List<Candidate> candidates = inputList.stream()
                .map(item -> {
                    Candidate candidate = null;
                    String name = getCandidateName(item);
                    if (match.isEmpty()    // load all candidates when match is not provided
                            || name.toLowerCase().contains(match)) {    // load candidate when matches
                        candidate = new Candidate(name, getCandidateOccurence(item), inputList.indexOf(item));
                    }
                    return candidate;
                })
                .filter(item -> item != null)
                .collect(Collectors.toList());

        // Sorting candidate list
        Collections.sort(candidates);
        System.out.println(">>>\tAutoCompletion sorted candidate list : " + candidates);

        // set result List<T> base on found candidates
        List<T> candidateItems = candidates.stream()
                .map(item -> {
                    return inputList.get(item.getIndex());
                })
                .limit(match.isEmpty() ? maxCandidateNumbersFull : maxCandidateNumbers) // or candidates.size() for full list
                .collect(Collectors.toList());

        return candidateItems;
    }

    public boolean validateFields() {

        // check fields type
        try {
            if (!type.getDeclaredField(candidateStringField).getType().getSimpleName().equals("String")) {
                System.out.println(">>> ERROR attribute " + candidateStringField + " is not a String class type");
                return false;
            }
            System.out.println(">>> attribute " + candidateStringField + " is a a String class type");
        } catch (NoSuchFieldException | SecurityException e1) {
            System.out.println(">>> ERROR attribute " + candidateStringField + " is not present");
            return false;
        }

        try {
            Field occurrenceField = type.getDeclaredField(candidateOccurrenceField);
            Class<?> fieldType = occurrenceField.getType();
            //System.out.println("occurrenceField type: " + fieldType );

            if (isClassCollection(fieldType)) { // check if occurrence Field is Collection, a Map
                System.out.println(">>> attribute " + candidateOccurrenceField +
                        " is a Collection class type: " + fieldType.getSimpleName());
                candidateOccurrenceFieldType = OccurrenceFieldType.collection;
            } else if (isClassNumber(fieldType) && Integer.class.equals(fieldType)) { // check if occurrence Field is a Number
                System.out.println(">>> attribute " + candidateOccurrenceField +
                        " is an Integer Number class type: " + fieldType.getSimpleName());
                candidateOccurrenceFieldType = OccurrenceFieldType.number;
            } else if (fieldType.isPrimitive() && int.class.equals(fieldType)) { // check if occurrence Field is a primitive
                System.out.println(">>> attribute " + candidateOccurrenceField +
                        " is a int primitive class type: " + fieldType.getSimpleName());
                candidateOccurrenceFieldType = OccurrenceFieldType.primitive;
            } else {
                System.out.println(">>> ERROR attribute " + candidateOccurrenceField +
                        " is not a Collection, a Integer Number or a int primitive class type: " + fieldType.getSimpleName());
                candidateOccurrenceFieldType = OccurrenceFieldType.other;
                return false;
            }
            //System.out.println(">>> candidateSetField = " + candidateSetField + " is present and is a Set");
        } catch (NoSuchFieldException | SecurityException e1) {
            System.out.println(">>> ERROR attribute " + candidateOccurrenceField + " is not present");
            return false;
        }

        System.out.println(">>>\tAutoCompletion is operational - fields are validated");
        return true;
    }

    public String getCandidateName(T inputItem) {
        String name = "";

        try {
            Field nameField = type.getDeclaredField(candidateStringField);
            nameField.setAccessible(true);
            name = (String) nameField.get(inputItem);
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
            System.out.println(">>> ERROR attribute " + candidateStringField + " cannot be catch");
            return null;
        }
        return name;
    }

    public Integer getCandidateOccurence(T inputItem) {
        Integer occurrence = null;

        try {
            Field occurrenceField = type.getDeclaredField(candidateOccurrenceField);
            occurrenceField.setAccessible(true);

            switch (candidateOccurrenceFieldType) {
                case collection:
                    //occurrence = new Long(((Collection<?>) occurrenceField.get(inputItem)).size());
                    occurrence = ((Collection<?>) occurrenceField.get(inputItem)).size();
                    break;
                case number:
                case primitive:
                    //occurrence = Long.valueOf((Integer) occurrenceField.get(inputItem));
                    occurrence = (Integer) occurrenceField.get(inputItem);
                    break;
                default:
                    System.out.println(">>> ERROR attribute " + candidateOccurrenceField + " is not a Collection or a Number class type");
            }
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
            System.out.println(">>> ERROR attribute " + candidateOccurrenceField + " cannot be catch");
        }
        return occurrence;
    }

    // check iF object is a collection / a Map
    public boolean isCollection(Object ob) {
        //return ob instanceof Collection || ob instanceof Map;
        return ob != null && isClassCollection(ob.getClass());
    }

    // check is Class is a collection / a Map class type
    public boolean isClassCollection(Class c) {
        return Collection.class.isAssignableFrom(c) || Map.class.isAssignableFrom(c);
    }

    public boolean isNumber(Object ob) {
        //return ob instanceof Number;
        return ob != null && isClassNumber(ob.getClass());
    }

    public boolean isClassNumber(Class cl) {
        return Number.class.isAssignableFrom(cl);
    }

}
