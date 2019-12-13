package service;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import datamodel.Board;
import datamodel.Course;
import datamodel.DynamoDbConnector;

public class BoardService {
    static DynamoDbConnector dynamoDb;
    DynamoDBMapper mapper;

    public BoardService() {
        dynamoDb = new DynamoDbConnector();
        dynamoDb.init();
        mapper = new DynamoDBMapper(dynamoDb.getClient());
    }

    public Board getBoard(String boardId){
        Board board=mapper.load(Board.class,boardId);
        return board;
    }
}
