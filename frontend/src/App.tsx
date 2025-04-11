import { useQuery } from '@tanstack/react-query';
import { fetchUsers } from './api/users';

function App() {
  const usersQuery = useQuery({ queryKey: ['users'], queryFn: fetchUsers });

  return (
    <div className='flex flex-col items-center justify-center min-h-svh'>
      {usersQuery.isLoading && <div>Loading users</div>}
      {usersQuery.isError && (
        <div>
          Error occurred while downloading the users: {usersQuery.error.message}
        </div>
      )}
      {usersQuery.data?.map((user) => (
        <div>
          <div>User id: {user.id}</div>
          <div>User email: {user.email}</div>
        </div>
      ))}
    </div>
  );
}

export default App;
