Javers 提供了三种核心的审计查询结果视图：Shadows（影子对象）、Changes（变更记录）、Snapshots（快照）。它们代表了审计数据的三种不同“观察视角”：

✅ 一句话理解
视图类型	意义	举例类比
Snapshots	历史版本数据的快照	“数据库历史快照表”
Changes	各个字段发生了什么变化	“字段级 diff 记录”
Shadows	历史对象重建（还原成 Java 对象）	“老版本的实体对象”

🧠 概念详解
1. 📸 Snapshots —— 快照
   快照是某一时刻的整个对象状态。它是 Javers 记录变更的基本单位。

✅ 特点：
每次 commit 都生成一个 Snapshot

包含对象全字段值的副本（即当时的 JSON 状态）

可以从中还原对象的状态（配合 Shadows）

每个 Snapshot 有 commitId、时间戳、作者 等元信息

📦 类：
java
复制
编辑
org.javers.core.snapshot.CdoSnapshot
📌 示例用途：
查出某个对象的所有版本历史

查看对象在某个时间点是什么状态

2. 🔍 Changes —— 变更记录
   是两个快照之间的差异 —— 逐字段的变更信息，包含变更类型和值变化。

✅ 特点：
表示某次提交中发生了哪些字段变化

可以逐条列出：字段名、旧值、新值

每条变更是一个 Change 实例

📦 相关类：
org.javers.core.diff.changetype.ValueChange

org.javers.core.diff.Change

org.javers.core.diff.Diff

📌 示例用途：
展示给用户看：哪些字段变了？变成什么了？

审计日志页面：[2024-06-01] 用户名 从 'admin' 改为 'superadmin'

3. 🧩 Shadows —— 影子对象
   Javers 根据 Snapshot 自动构造的“历史实体副本”对象（即还原出来的旧 Java 对象）

✅ 特点：
类型是用户自定义的实体类（如 User、Role）

数据来自于 Snapshot 的状态

可用于还原对象旧状态，做版本比较、回滚操作等

📦 类：
java
复制
编辑
org.javers.core.shadow.Shadow<T>
📌 示例用途：
查询并还原“某对象在 3 天前的状态”

构建时间线/历史回放功能

🔄 三者关系图示
css
复制
编辑
[Commit A]      [Commit B]
↓               ↓
Snapshot A      Snapshot B
\            /
\__________/      → Changes（字段 diff）
↓
Shadow A     ← 重建对象状态
✅ 示例代码（查询全部三类）
java
复制
编辑
JqlQuery query = QueryBuilder.byInstanceId(123L, User.class).build();

List<CdoSnapshot> snapshots = javers.findSnapshots(query); // 快照
List<Change> changes = javers.findChanges(query);          // 字段变更
List<Shadow<User>> shadows = javers.findShadows(query);    // 历史对象状态
✅ 总结对比
类型	内容	用途	类
Snapshot	全字段副本	查询版本记录、元信息	CdoSnapshot
Change	字段变更 diff	展示字段变更详情	ValueChange, ObjectRemoved 等
Shadow	实体类旧对象	回放、还原旧状态	Shadow<T>

如果你正在构建审计 UI 页面，可以这样设计：

左侧：时间轴（根据 Snapshots）

右侧：字段变更详情（根据 Changes）

回显：历史对象状态（根据 Shadows）