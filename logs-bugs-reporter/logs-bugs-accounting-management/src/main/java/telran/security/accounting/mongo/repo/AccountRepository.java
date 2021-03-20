package telran.security.accounting.mongo.repo;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import telran.security.accounting.mongo.documents.AccountDoc;
public interface AccountRepository extends MongoRepository<AccountDoc, ObjectId>, UpdateMongoOperations {

	Long deleteByUserName(String userName);
	
}
