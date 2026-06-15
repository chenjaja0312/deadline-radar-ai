const form = document.querySelector('#assignmentForm');
const list = document.querySelector('#list');
const dashboard = document.querySelector('#dashboard');

function todayPlus(days) {
  const d = new Date();
  d.setDate(d.getDate() + days);
  return d.toISOString().slice(0,10);
}
document.querySelector('input[name="deadline"]').value = todayPlus(3);

form.addEventListener('submit', async (e) => {
  e.preventDefault();
  const data = Object.fromEntries(new FormData(form).entries());
  ['difficulty','progress','estimatedHours','mood'].forEach(k => data[k] = Number(data[k]));
  const res = await fetch('/api/assignments', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(data)
  });
  if (!res.ok) { alert('新增失敗，請確認欄位。'); return; }
  form.reset();
  document.querySelector('input[name="deadline"]').value = todayPlus(3);
  await loadAll();
});

async function removeItem(id) {
  await fetch(`/api/assignments/${id}`, { method: 'DELETE' });
  await loadAll();
}

async function loadDashboard() {
  const d = await (await fetch('/api/dashboard')).json();
  const top = d.topRisk ? `${d.topRisk.course}｜${d.topRisk.title}` : '無資料';
  dashboard.innerHTML = `
    <div class="card"><h3>總任務數</h3><div class="num">${d.total}</div></div>
    <div class="card"><h3>平均風險分數</h3><div class="num">${d.averageRisk}</div></div>
    <div class="card"><h3>最高風險任務</h3><div>${top}</div></div>
    <div class="card"><h3>風險分布</h3><div>High ${d.riskCounts.High || 0}｜Medium ${d.riskCounts.Medium || 0}｜Low ${d.riskCounts.Low || 0}</div></div>
  `;
}

async function loadList() {
  const data = await (await fetch('/api/assignments')).json();
  list.innerHTML = data.map(a => `
    <article class="item">
      <div>
        <h3>${a.course}｜${a.title}</h3>
        <div class="meta">截止：${a.deadline}｜難度：${a.difficulty}/5｜完成度：${a.progress}%｜預估：${a.estimatedHours} 小時｜心情：${a.mood}/5</div>
        <div class="suggestion">AI 建議：${a.suggestion}</div>
      </div>
      <div>
        <span class="badge ${a.riskLevel}">${a.riskLevel} ${a.riskScore}</span><br><br>
        <button onclick="removeItem(${a.id})">刪除</button>
      </div>
    </article>
  `).join('') || '<p>目前沒有任務，先新增一筆吧。</p>';
}

async function loadAll() {
  await Promise.all([loadDashboard(), loadList()]);
}
loadAll();
